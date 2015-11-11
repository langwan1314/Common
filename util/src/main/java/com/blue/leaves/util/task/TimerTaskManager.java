package com.blue.leaves.util.task;


import com.blue.leaves.util.L;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskManager {
    public static class TimerTaskItem extends TimerTask {
        private Runnable taskRunnable;
        private TimerTaskManager mTaskMgr;
        private boolean mIsPeriod;
        private boolean isNeedRunOnUIThread;
        private int id;

        public TimerTaskItem(TimerTaskManager mgr, int id) {
            super();
            mTaskMgr = mgr;
            this.id = id;
            mIsPeriod = false;
            isNeedRunOnUIThread = false;
        }

        @Override
        public void run() {
            if (isNeedRunOnUIThread
                    ) {
                //    Utils.getAppContext()
                //            .runOnUIThread(this);
                return;
            }

            synchronized (TimerTaskItem.this) {

                if (taskRunnable == null) {
                    return;
                }

                taskRunnable.run();
                if (!mIsPeriod) {
                    mTaskMgr.afterRun(this);
                }
            }

        }

        @Override
        public String toString() {
            return "id: " + id + "is period : " + mIsPeriod;
        }

        public void setTaskJob(Runnable task) {
            taskRunnable = task;
        }
    }

    // 内部类 用于线程安全和延迟加载 利用java自身机制

    private static class TimerTaskManagerHolder {
        private static TimerTaskManager mInstence = new TimerTaskManager();
    }

    private static final String TAG = "TimerTaskManager";

    HashMap<String, TimerTaskItem> mWorkingGroup = new HashMap<String, TimerTaskItem>();

    private final static String mIDPrefix = "TimerTask_ID_";
    private int nextID = 0;

    private TimerTaskManager() {
        if (mTimer == null) {
            mTimer = new Timer(TAG);
        }
    }

    public static TimerTaskManager getInstance() {
        return TimerTaskManagerHolder.mInstence;
    }

    private Timer mTimer;

    public String addTimerTask(Runnable task, long delay,
                               boolean isNeedRunOnUIThread) {
        return addTimerTask(task, delay, -1,
                isNeedRunOnUIThread);
    }

    public String addTimerTask(Runnable task, long delay) {
        return addTimerTask(task, delay, -1);
    }

    public String addTimerTask(Runnable task, long delay,
                               long period) {
        return addTimerTask(task, delay, period, false);
    }

    public String addTimerTask(Runnable task, long delay,
                               long period, boolean isNeedRunOnUIThread) {
        if (delay < 0) {
            delay = 0;
        }

        if (mTimer != null && task != null) {
            try {
                TimerTaskItem item = obtainItem();
                item.setTaskJob(task);
                item.isNeedRunOnUIThread = isNeedRunOnUIThread;

                synchronized (TimerTaskManager.this) {
                    if (period <= 0) {
                        item.mIsPeriod = false;
                        mTimer.schedule(item, delay);
                    } else {
                        item.mIsPeriod = true;
                        mTimer.schedule(item, delay, period);
                    }
                    mWorkingGroup.put(mIDPrefix + item.id,
                            item);
                }

                return mIDPrefix + item.id;
            } catch (IllegalStateException e) {
                // task is already schedule or canceled or timer is stoped
                e.printStackTrace();
            }
        }
        return null;
    }

    private void afterRun(TimerTaskItem item) {
        if (item == null) {
            return;
        }
        synchronized (TimerTaskManager.this) {
            mWorkingGroup.remove(mIDPrefix + item.id);
        }
    }

    public void cancelTimerTask(String taskID) {
        // SLog.i("TimerTaskManager", "cancel " + taskID);
        if (taskID != null && taskID.length() != 0) {
            TimerTaskItem task = null;

            synchronized (TimerTaskManager.this) {
                if (mWorkingGroup.containsKey(taskID)) {
                    task = mWorkingGroup.get(taskID);
                    mWorkingGroup.remove(taskID);
                }
            }
            if (task != null) {
                synchronized (task) {
                    try {
                        task.cancel();

                        task.mIsPeriod = false;
                        task.taskRunnable = null;
                        task.mTaskMgr = null;
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    private TimerTaskItem obtainItem() {
        synchronized (TimerTaskManager.this) {
            return new TimerTaskItem(this, nextID++);
        }
    }

    // TODO 调试用用于打印出Timer情况，查看内存泄露情况
    public synchronized void dumpWorkingGroup() {
        if (mWorkingGroup != null
                && !mWorkingGroup.isEmpty()) {
            L.i("TimerTaskManager",
                    "dump Current TimerTask : size -> "
                            + mWorkingGroup.size());
            Iterator<TimerTaskItem> iter = mWorkingGroup
                    .values().iterator();
            while (iter.hasNext()) {
                TimerTaskItem item = iter.next();
                L.d("TimerTaskManager", item.toString());
            }
        }
    }
}
