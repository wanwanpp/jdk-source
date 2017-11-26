package com.jdk.test;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * SynchronousQueue的容量为0，当offer一个元素时，若没有线程对其进行take操作，就会返回false，除非offer操作前，就有take操作执行一直阻塞，等待offer元素进去。
 * 若offer设置了timeout，在timeout时间内，有线程进行take，也可以返回true
 */
public class TestSynchronousQueue {
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        Producer p1 = new Producer("p1", queue, 10);
        Producer p2 = new Producer("p2", queue, 50);

        Consumer c1 = new Consumer("c1", queue);
        Consumer c2 = new Consumer("c2", queue);

//        c1.start();
//        TimeUnit.MILLISECONDS.sleep(100);
//        c2.start();
//        TimeUnit.MILLISECONDS.sleep(100);
//        p1.start();
//        TimeUnit.MILLISECONDS.sleep(100);
//        p2.start();
//        TimeUnit.MILLISECONDS.sleep(100);
        c1.start();


    }

    static class Producer extends Thread {
        private SynchronousQueue<Integer> queue;
        private int n;

        public Producer(String name, SynchronousQueue<Integer> queue, int n) {
            super(name);
            this.queue = queue;
            this.n = n;
        }

        public void run() {
            try {
                System.out.println(getName() + " offer result " + queue.offer(n,2,TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread {
        private SynchronousQueue<Integer> queue;

        public Consumer(String name, SynchronousQueue<Integer> queue) {
            super(name);
            this.queue = queue;
        }

        public void run() {
            try {
                System.out.println(getName() + " take result " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}