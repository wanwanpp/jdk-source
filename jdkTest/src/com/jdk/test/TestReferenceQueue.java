package com.jdk.test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author 王萍
 * @date 2017/11/28 0028
 */

//  博客：https://vinoit.me/2017/06/23/java-phantom-reference/     http://www.cnblogs.com/jabnih/p/6580665.html
public class TestReferenceQueue {

    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue queue = new ReferenceQueue();

        Object o = new Object();
        WeakReference reference = new WeakReference(o, queue);
        //和WeakReference的效果一样会产生GC。
        PhantomReference reference2 = new PhantomReference(o, queue);
        //使用SoftReference时，System.gc();没有引发GC。
        SoftReference reference3 = new SoftReference(new Object(), queue);
        System.out.println(reference);
        System.out.println(queue.remove(500));   //若不设置remove方法的超时时间，会一直等待
        o = null;
        System.gc();

        //GC后，PhantomReference中对应的queue的referent属性为被引用对象，而WeakReference对应的queue的referent属性为null。这里不是太明白，好像是GC collector对不同Reference的不同操作
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
    }
}
