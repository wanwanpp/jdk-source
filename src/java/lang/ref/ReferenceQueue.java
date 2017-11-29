/*
 * Copyright (c) 1997, 2005, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.lang.ref;

/**
 * Reference queues, to which registered reference objects are appended by the
 * garbage collector after the appropriate reachability changes are detected.
 * 在检测到可达性发生变化是会将对应的注册到此队列的Reference添加到此队列中。
 *
 * @author   Mark Reinhold
 * @since    1.2
 */
//创建Reference时，将Queue注册到Reference中，当该Reference所引用的对象被垃圾收集器回收时，
// 会将该Reference放到该队列中，相当于一种通知机制。
public class ReferenceQueue<T> {

    /**
     * Constructs a new reference-object queue.
     */
    public ReferenceQueue() { }

    private static class Null extends ReferenceQueue {
        boolean enqueue(Reference r) {
            return false;
        }
    }

    //标识没有注册queue
    static ReferenceQueue NULL = new Null();
    //标识已经处于对应的Queue中
    static ReferenceQueue ENQUEUED = new Null();

    static private class Lock { };
    //用于同步用户的remove和poll操作和ReferenceHandler的enqueue操作。
    private Lock lock = new Lock();
    //队列
    private volatile Reference<? extends T> head = null;
    //队列元素个数
    private long queueLength = 0;

    boolean enqueue(Reference<? extends T> r) { /* Called only by Reference class */
        synchronized (r) {
            if (r.queue == ENQUEUED) return false;
            synchronized (lock) {
                //设置状态
                r.queue = ENQUEUED;
                //头插法插入新的节点r。
                r.next = (head == null) ? r : head;
                head = r;
                queueLength++;
                if (r instanceof FinalReference) {
                    sun.misc.VM.addFinalRefCount(1);
                }
                //插入了新节点，通知当前挂起的线程（调用remove时有可能会挂起）
                lock.notifyAll();
                return true;
            }
        }
    }

    //取出头结点
    private Reference<? extends T> reallyPoll() {       /* Must hold lock */
        if (head != null) {
            Reference<? extends T> r = head;
            head = (r.next == r) ? null : r.next;
            //将Reference的引用队列改为Reference.NULL对象，表示此Reference此时未注册引用队列
            r.queue = NULL;
            r.next = r;
            queueLength--;
            if (r instanceof FinalReference) {
                sun.misc.VM.addFinalRefCount(-1);
            }
            return r;
        }
        return null;
    }

    /**
     * Polls this queue to see if a reference object is available.  If one is
     * available without further delay then it is removed from the queue and
     * returned.  Otherwise this method immediately returns <tt>null</tt>.
     *
     * @return  A reference object, if one was immediately available,
     *          otherwise <code>null</code>
     */
    public Reference<? extends T> poll() {
        if (head == null)
            return null;
        synchronized (lock) {
            return reallyPoll();
        }
    }

    /**
     * Removes the next reference object in this queue, blocking until either
     * one becomes available or the given timeout period expires.
     *
     * <p> This method does not offer real-time guarantees: It schedules the
     * timeout as if by invoking the {@link Object#wait(long)} method.
     *
     * @param  timeout  If positive, block for up to <code>timeout</code>
     *                  milliseconds while waiting for a reference to be
     *                  added to this queue.  If zero, block indefinitely.
     *
     * @return  A reference object, if one was available within the specified
     *          timeout period, otherwise <code>null</code>
     *
     * @throws  IllegalArgumentException
     *          If the value of the timeout argument is negative
     *
     * @throws  InterruptedException
     *          If the timeout wait is interrupted
     */
    //拿出引用队列中的头结点Reference
    public Reference<? extends T> remove(long timeout)
        throws IllegalArgumentException, InterruptedException
    {
        if (timeout < 0) {
            throw new IllegalArgumentException("Negative timeout value");
        }
        synchronized (lock) {
            //从队列中取出一个元素
            Reference<? extends T> r = reallyPoll();
//            如果不为空则返回这个元素
            if (r != null) return r;
            //r为空则等待新节点的加入
            for (;;) {
                //挂起
                lock.wait(timeout);
                //被中断或者唤醒，继续执行，取出一个元素
                r = reallyPoll();
                if (r != null) return r;
                if (timeout != 0) return null;
            }
        }
    }

    /**
     * Removes the next reference object in this queue, blocking until one
     * becomes available.
     *
     * @return A reference object, blocking until one becomes available
     * @throws  InterruptedException  If the wait is interrupted
     */
    public Reference<? extends T> remove() throws InterruptedException {
        return remove(0);
    }

}
