package com.jdk.test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by 王萍 on 2017/11/6 0006.
 */
public class TestUnsafe {

    //因为Unsafu对象无法通过new和getUnsafe方法获取Unsafe的对象
    //这里通过反射获取theUnsafe字段来获取。
    public static void main(String[] args) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {
        // 通过反射得到theUnsafe对应的Field对象
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        // 设置该Field为可访问
        field.setAccessible(true);
        // 通过Field得到该Field对应的具体对象，传入null是因为该Field为static的
        Unsafe unsafe = (Unsafe) field.get(null);
        System.out.println(unsafe);

    }
}
