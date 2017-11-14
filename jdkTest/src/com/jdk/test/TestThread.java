package com.jdk.test;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by 王萍 on 2017/11/14 0014.
 */
public class TestThread {

    //单元测试和main的测试不一样，单元测试中main函数做完了，就直接退出jvm了。
    @Test
    public void testJoin() throws InterruptedException {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new WorkThread(previous));
            thread.start();
            previous = thread;
        }
        TimeUnit.SECONDS.sleep(3);
        System.out.println(previous.isDaemon());
    }

    public static void main(String[] args) throws InterruptedException {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new WorkThread(previous));
            thread.start();
            previous = thread;
        }
        TimeUnit.SECONDS.sleep(3);
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
            System.out.println(previous.isAlive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " is running");
    }
}