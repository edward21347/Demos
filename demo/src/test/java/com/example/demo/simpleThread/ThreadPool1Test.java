package com.example.demo.simpleThread;

import java.util.concurrent.LinkedBlockingQueue;

class ThreadPool1Test {
    public static void main(String[] args) {
        ThreadPool1 threadPool = new ThreadPool1(3, new LinkedBlockingQueue<>(5));
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            System.out.println("第"+taskId+"个任务提交");
            threadPool.execute(() -> {
                System.out.println("第" + taskId + "个任务:" + Thread.currentThread().getName() + " is working");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第" + taskId + "个任务:" + Thread.currentThread().getName() + " is done");
            });
            System.out.println("第"+taskId+"个任务提交完毕");
        }
    }
}