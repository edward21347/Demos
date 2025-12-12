package com.example.demo.simpleThread;


import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 基于阻塞队列简易线程池，加入拒绝策略，超时回收机制
 */
public class ThreadPool2 {

    private final BlockingQueue<Runnable> workerQueue;

    private final List<Worker> workers = new ArrayList<>();

    // 线程池核心线程数
    private final int corePoolSize;

    // 线程池最大线程数
    private final int maximumPoolSize;

    // 非核心线程空闲时间
    private final long keepAliveTime;

    public ThreadPool2(BlockingQueue<Runnable> workerQueue, int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.workerQueue = workerQueue;
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    public void execute(Runnable task) {
        Assert.notNull(task, "task is null");
        if(workers.size() < corePoolSize){
            this.addWorker(task,true);
            return;
        }

        boolean enqueued = workerQueue.offer(task);
        if(enqueued){
            return;
        }

        if(!this.addWorker(task,false)){
            throw new RuntimeException("拒绝策略");
        }
    }

    private boolean addWorker(Runnable task, boolean core){
        int wc = workers.size();
        if(wc >= (core ? corePoolSize : maximumPoolSize)){
            return false;
        }
        Worker worker = new Worker(task);
        workers.add(worker);
        worker.getThread().start();
        return true;
    }

    @Getter
    @Setter
    private class Worker implements Runnable {
        private Thread thread;
        private Runnable task;

        public Worker(Runnable task){
            this.task = task;
            thread = new Thread(this);
        }

        @Override
        public void run() {
            try {
                while (task != null || (task = getTaskFromQueue()) != null) {
                    task.run();
                    task = null;
                }
            } finally {
                System.out.println(thread.getName() + "线程销毁");
                workers.remove(this);
            }
        }
    }

    private Runnable getTaskFromQueue(){
        boolean timeOut = false;
        while (true){
            // 超过核心线程数，尝试销毁一部分线程
            boolean tryDestroy = workers.size() > corePoolSize;

            if(tryDestroy && timeOut){
                return null;
            }

            try {
                Runnable r = tryDestroy ? workerQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) : workerQueue.take();
                if(r != null){
                    return r;
                }
                timeOut = true;
            } catch (InterruptedException e) {
                timeOut = false;
            }
        }
    }


}
