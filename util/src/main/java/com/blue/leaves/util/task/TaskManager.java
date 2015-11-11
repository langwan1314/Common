package com.blue.leaves.util.task;

public class TaskManager {

    /**
     * 
     * 开始一个异步任务，创建线程
     */
    public static void startRunnableRequest(
            Runnable runnable) {
        RunnablePool.getInstance().addTask(runnable);
    }

    /**
     * 开始一个 异步任务，加入线程池，不需要创建线程
     */
    public static void startRunnableRequestInPool(
            Runnable runnable) {
        RunnablePool.getInstance()
                .addTaskIntoPool(runnable);
    }
}
