package com.jdk.test;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * @author 王萍
 * @date 2017/12/9 0009
 */
public class TestHashTable {

    public static void main(String[] args) {
        testHashtableAPIs();
    }

    private static void testHashtableAPIs() {
        // 初始化随机种子
        Random r = new Random();
        // 新建Hashtable
        Hashtable table = new Hashtable();
        // 添加操作
        table.put("one", r.nextInt(10));
        table.put("two", r.nextInt(10));
        table.put("three", r.nextInt(10));

        // 打印出table
        System.out.println("table:" + table);

        // 通过Iterator遍历key-value
        Iterator iter = table.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            System.out.println("next : " + entry.getKey() + " - " + entry.getValue());
        }

        // Hashtable的键值对个数
        System.out.println("size:" + table.size());

        // containsKey(Object key) :是否包含键key
        System.out.println("contains key two : " + table.containsKey("two"));
        System.out.println("contains key five : " + table.containsKey("five"));

        // containsValue(Object value) :是否包含值value
        System.out.println("contains value 0 : " + table.containsValue(new Integer(0)));

        // remove(Object key) ： 删除键key对应的键值对
        table.remove("three");

        System.out.println("table:" + table);

        // clear() ： 清空Hashtable
        table.clear();

        // isEmpty() : Hashtable是否为空
        System.out.println((table.isEmpty() ? "table is empty" : "table is not empty"));
    }

}
