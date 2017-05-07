package com.jdk.test;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 王萍 on 2017/5/6 0006.
 */
public class TestTreeMap {


    public static void main(String[] args) {


        //Map<String, String> map = new TreeMap<>();

        //忽略大小写
//        Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        //Collections类有一个静态方法reverseOrder()可以返回一个逆序比较器
        Map<String, String> map = new TreeMap<>(Collections.reverseOrder());

        //自定义比较器，逆序并且忽略大小写比较字符串
//        Map<String,String> map = new TreeMap<>(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o2.compareToIgnoreCase(o1);
//            }
//        });
        map.put("a", "abstract");
        map.put("c", "call");
        map.put("b", "basic");
        map.put("T", "tree");

        for (Map.Entry<String, String> kv : map.entrySet()) {
            System.out.print(kv.getKey() + "=" + kv.getValue() + " ");
        }

        System.out.println("\n=================");

        //当map1.put("t", "try");时，由于大小写忽略，发现T已经存在，所以键不变，值更新为try
        Map<String, String> map1 = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        map1.put("T", "tree");
        map1.put("t", "try");

        for (Map.Entry<String, String> kv : map1.entrySet()) {
            System.out.print(kv.getKey() + "=" + kv.getValue() + " ");
        }
    }

    @Test
    public void testCompareDate() {

        //大小比较不是期望的结果，按照了字符串的比较方式
        Map<String, Integer> map = new TreeMap<>();
        map.put("2016-7-3", 100);
        map.put("2016-7-10", 120);
        map.put("2016-8-1", 90);

        for (Map.Entry<String, Integer> kv : map.entrySet()) {
            System.out.println(kv.getKey() + "," + kv.getValue());
        }
        System.out.println("\n=================");

        Map<String, Integer> map1 = new TreeMap<>(new Comparator<String>() {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public int compare(String o1, String o2) {
                try {
                    return dateFormat.parse(o1).compareTo(dateFormat.parse(o2));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });

        map1.put("2016-7-3", 100);
        map1.put("2016-7-10", 120);
        map1.put("2016-8-1", 90);
        for (Map.Entry<String, Integer> kv : map1.entrySet()) {
            System.out.println(kv.getKey() + "," + kv.getValue());
        }
    }

    @Test
    public void testNavigableMap(){

        NavigableMap<String,String> map = new TreeMap<>();

        map.put("a", "abstract");
        map.put("f", "final");
        map.put("c", "call");

        for (Map.Entry<String,String> entry:map.entrySet()){
            System.out.println(entry.getKey()+"="+entry.getValue());
        }

        //输出：a=abstract
//        System.out.println(map.firstEntry());

        //输出：f=final
//        System.out.println(map.lastEntry());

        //输出：c=call
        System.out.println(map.floorEntry("d"));

        //输出：f=final
        System.out.println(map.ceilingEntry("d"));

        //输出：{c=call, a=abstract}
        System.out.println(map.descendingMap().subMap("f", true, "a", true));
        System.out.println(map.descendingMap().subMap("f", false, "a", true));
    }
}
