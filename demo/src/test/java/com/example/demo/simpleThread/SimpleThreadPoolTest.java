package com.example.demo.simpleThread;

import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class SimpleThreadPoolTest {
    public static void main(String[] args) {
        LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

        SimpleThreadPool threadPool = new SimpleThreadPool(5, taskQueue);

        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            System.out.println("第"+taskId+"个任务提交");
            threadPool.execute(()->{
                System.out.println("第"+taskId+"个任务:"+Thread.currentThread().getName() + " is working");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第"+taskId+"个任务:"+Thread.currentThread().getName() + " is done");
            });
            System.out.println("第"+taskId+"个任务提交完毕");
        }

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}