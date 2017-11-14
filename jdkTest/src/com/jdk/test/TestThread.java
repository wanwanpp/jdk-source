package com.jdk.test;

import org.junit.Test;

/**
 * Created by 王萍 on 2017/11/14 0014.
 */
public class TestThread {

    @Test
    public void testJoin() throws InterruptedException {
//        Thread previous = Thread.currentThread();
        Thread previous = null;
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new WorkThread(previous), String.valueOf(i));
            thread.start();
            previous=thread;
        }
        Thread.sleep(10000);
    }
}

class WorkThread implements Runnable {

    private Thread previous;

    public WorkThread(Thread previous) {
        this.previous = previous;
    }

    @Override
    public void run() {
        try {
            previous.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " is running");
    }
}