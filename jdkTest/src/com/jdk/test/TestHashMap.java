package com.jdk.test;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author Õı∆º
 * @date 2017/12/8 0008
 */
public class TestHashMap {

    @Test
    public void indexForBucket() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HashMap<String, String> map = new HashMap<>();
        Method hash = map.getClass().getDeclaredMethod("hash", Object.class);
        hash.setAccessible(true);
        int hashOfWanwanpp = (int)hash.invoke(map, "wanwanpp");
        System.out.println(Integer.toBinaryString(hashOfWanwanpp));
    }
}
