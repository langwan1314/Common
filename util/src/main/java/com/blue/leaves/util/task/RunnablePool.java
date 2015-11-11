package com.blue.leaves.util.task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RunnablePool {
    private static RunnablePool instance = null;
    // private ExecutorService pool = Executors.newFixedThreadPool(15);
    private int maxSize = 16;// >1
    private int step = 1;
    private ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(
            maxSize);
    /*
     * 线程池类为 java.util.concurrent.ThreadPoolExecutor，常用构造方法为：
     * ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long
     * keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
     * RejectedExecutionHandler handler) corePoolSize： 线程池维护线程的最少数量
     * maximumPoolSize：线程池维护线程的最大数量 keepAliveTime： 线程池维护线程所允许的空闲时间 unit：
     * 线程池维护线程所允许的空闲时间的单位 workQueue： 线程池所使用的缓冲队列 handler： 线程池对拒绝任务的处理策略 一个任务通过
     * execute(Runnable)方法被添加到线程池，任务就是一个
     * Runnable类型的对象，任务的执行方法就是Runnable类型对象的run()方法。
     * 当一个任务通过execute(Runnable)方法欲添加到线程池时： l
     * 如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。 l
     * 如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。 l
     * 如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，
     * 建新的线程来处理被添加的任务。 l
     * 如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过
     * handler所指定的策略来处理此任务
     * 。也就是：处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、最大线程maximumPoolSize
     * ，如果三者都满了，使用handler处理被拒绝的任务。 l 当线程池中的线程数量大于
     * corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。
     */
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 2, 2 * 1000, TimeUnit.MILLISECONDS, queue,
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static RunnablePool getInstance() {
        if (instance == null) {

            instance = new RunnablePool();
        }
        return instance;
    }

    public void addTask(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void addTaskIntoPool(Runnable runnable) {
        executor.execute(runnable);
    }

    public boolean poll(int n) {
        for (int i = 0; i < n; ++i) {
            Runnable r = RunnablePool.getInstance().executor
                    .getQueue().poll();
            if (null == r) {
                return true;
            }
        }
        return 0 == RunnablePool.getInstance().executor
                .getQueue().size();
    }

    public int size() {
        return RunnablePool.getInstance().executor
                .getQueue().size();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getStep() {
        return RunnablePool.getInstance().step;
    }

    public boolean isReady() {
        return RunnablePool.getInstance().size() < RunnablePool
                .getInstance().getMaxSize();
    }
}
