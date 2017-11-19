package com.jdk.test.blockingqueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @param <T>
 * @author 王萍
 * @date 2017/11/19
 */
public class BlockingArrayWithCondition<T> {
    private final T[] array;

    private int head;

    private int tail;

    private int count;

    private java.util.concurrent.locks.Lock lock = new ReentrantLock();

    private Condition isFull = lock.newCondition();

    private Condition isEmpty = lock.newCondition();

    public BlockingArrayWithCondition(int size) {
        array = (T[]) new Object[size];
    }

    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            while (count == array.length) {
                isFull.await();
            }

            array[tail] = item;
            if (++tail == array.length) {
                tail = 0;
            }
            count++;
            System.out.println("Add item: " + item);
            // 通知isEmpty条件队列有元素进入  
            isEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                isEmpty.await();
            }

            T item = array[head];
            if (++head == array.length) {
                head = 0;
            }
            count--;
            System.out.println("Take item: " + item);
            // 通知isFull条件队列有元素出去  
            isFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }
}  