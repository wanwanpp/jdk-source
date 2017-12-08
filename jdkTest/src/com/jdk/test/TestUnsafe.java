package com.jdk.test;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by 王萍 on 2017/11/6 0006.
 */
public class TestUnsafe {

    //因为Unsafu对象无法通过new和getUnsafe方法获取Unsafe的对象
    //这里通过反射获取theUnsafe字段来获取。
//    public static void main(String[] args) throws NoSuchFieldException,
//            SecurityException, IllegalArgumentException, IllegalAccessException {
//        // 通过反射得到theUnsafe对应的Field对象
//        Field field = Unsafe.class.getDeclaredField("theUnsafe");
//        // 设置该Field为可访问
//        field.setAccessible(true);
//        // 通过Field得到该Field对应的具体对象，传入null是因为该Field为static的
//        Unsafe unsafe = (Unsafe) field.get(null);
//        System.out.println(unsafe);
//
//    }


    @Test
    public void testUnsafePut() throws NoSuchFieldException, IllegalAccessException {
        // 通过反射得到theUnsafe对应的Field对象
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        // 设置该Field为可访问
        field.setAccessible(true);
        // 通过Field得到该Field对应的具体对象，传入null是因为该Field为static的
        Unsafe unsafe = (Unsafe) field.get(null);

        User user = new User();
        System.out.println(user); //打印test,1,2,1.72

        Class userClass = user.getClass();
        Field name = userClass.getDeclaredField("name");
        Field id = userClass.getDeclaredField("id");
        Field age = userClass.getDeclaredField("age");
        Field height = userClass.getDeclaredField("height");
        //直接往内存地址写数据
        unsafe.putObject(user, unsafe.objectFieldOffset(name), "midified-name");
        unsafe.putLong(user, unsafe.objectFieldOffset(id), 100l);
        unsafe.putInt(user, unsafe.objectFieldOffset(age), 101);
        unsafe.putDouble(user, unsafe.objectFieldOffset(height), 100.1);

        System.out.println(user);//打印midified-name,100,101,100.1
    }

    @Test
    //在指定address写入char。
    //操作不成功，直接fatal error。
    public void testPutChar() throws Exception {
        char s = 'x';
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        unsafe.putChar(System.identityHashCode(s), '2');
    }
}

class User {
    private String name = "test";
    private long id = 1;
    private int age = 2;
    private double height = 1.72;


    @Override
    public String toString() {
        return name + "," + id + "," + age + "," + height;
    }
}

