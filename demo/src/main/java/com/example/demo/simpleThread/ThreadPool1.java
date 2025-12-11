package com.example.demo.simpleThread;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 基于阻塞队列简易线程池，加入拒绝策略
 */
public class ThreadPool1 {

    private final BlockingQueue<Runnable> workerQueue;

    private final List<Worker> workers = new ArrayList<>();

    private final int poolSize;
    public ThreadPool1(int poolSize, BlockingQueue<Runnable> workQueue) {
        this.workerQueue = workQueue;
        this.poolSize = poolSize;
    }

    public void execute(Runnable task) {
        if(workers.size() < poolSize){
            this.addWorker(task);
            return;
        }

        boolean enqueued = workerQueue.offer(task);
        if(!enqueued){
            throw new RuntimeException("拒绝策略");
        }
    }

    private void addWorker(Runnable task){
        Worker worker = new Worker(task);
        workers.add(worker);
        worker.getThread().start();
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
            while (task != null || (task = getTaskFromQueue()) != null) {
                task.run();
                task = null;
            }
        }
    }

    private Runnable getTaskFromQueue(){
        try {
            return workerQueue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }
}
