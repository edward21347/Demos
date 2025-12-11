package com.example.demo.simpleThread;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 基于阻塞队列简易线程池
 */
public class SimpleThreadPool {

    private final BlockingQueue<Runnable> taskQueue;

    private final List<Worker> workers = new ArrayList<>();

    public SimpleThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
        this.taskQueue = workQueue;
        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            worker.start();
        }
    }

    public void execute(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable task = null;
                try {
                    task = taskQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                task.run();
            }
        }
    }
}
