package com.jdk.test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author 王萍
 * @date 2017/11/28 0028
 */
public class TestReferenceQueue {

    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue queue = new ReferenceQueue();

        WeakReference reference = new WeakReference(new Object(), queue);
        //和WeakReference的效果一样。
        //        PhantomReference reference = new PhantomReference(new Object(), queue);
        //使用SoftReference时，System.gc();没有引发GC。
//        SoftReference reference = new SoftReference(new Object(), queue);
        System.out.println(reference);
        System.out.println(queue.remove(1000));   //若不设置remove方法的超时时间，会一直等待
        System.gc();

        Reference reference1 = queue.remove();
        System.out.println(reference1);
    }
}
