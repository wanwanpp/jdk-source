package com.jdk.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by 王萍 on 2017/11/17 0017.
 */
//使用AtomicReference来把多个引用变量放在一个引用对象中。达到将多个共享变量原子更新的目的。
//以下示例中，integer和string变量为多个共享变量，将他们简单的装到了一个list中，使用AtomicReference对list进行原子更新。
// 这里装到list中更新不是太优雅。
public class TestAtomicReference {

    public static void main(String[] args) {
        Integer integer = 19;
        String string = "wanwanpp";

        List list = new ArrayList();
        list.add(integer);
        list.add(string);

        AtomicReference reference = new AtomicReference(list);

        List newlist = new ArrayList();
        newlist.add(integer + 10);
        newlist.add(string + "222");

        reference.compareAndSet(list, newlist);

        Object o = reference.get();
        System.out.println(o);

    }
}
