package com.jdk.test;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author 王萍
 * @date 2017/12/8 0008
 */
public class TestHashMap {

    @Test
    public void indexForBucket() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HashMap<String, String> map = new HashMap<>();
        Method hash = map.getClass().getDeclaredMethod("hash", Object.class);
        hash.setAccessible(true);
        int hashOfWanwanpp = (int) hash.invoke(map, "wanwanpp");
        System.out.println(Integer.toBinaryString(hashOfWanwanpp));
    }

    //下面程序会导致cpu100，HashMap
    public static void main(String[] args) throws InterruptedException {
        final HashMap<String, String> map = new HashMap<String, String>(2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    }, "ftf" + i).start();
                }
            }
        }, "ftf");
        t.start();
        t.join();
    }
}
