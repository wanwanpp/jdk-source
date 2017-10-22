package com.jdk.test;

import org.junit.Test;

import java.util.*;

/**
 * Created by 王萍 on 2017/5/7 0007.
 */
public class TestTreeSet {

    @Test
    public void testMain() {
        Set<String> words = new TreeSet<String>();
        words.addAll(Arrays.asList(new String[]{
                "tree", "map", "hash", "map",
        }));
        for (String w : words) {
            System.out.print(w + " ");
        }
    }

    @Test
    public void testSortedSet() {
        SortedSet<String> set = new TreeSet<String>();
        set.addAll(Arrays.asList(new String[]{
                "c", "a", "b", "d", "f"
        }));

        System.out.println(set.first()); //a
        System.out.println(set.last()); //f
        System.out.println(set.headSet("b"));//[a]
        System.out.println(set.tailSet("d"));//[d, f]
        System.out.println(set.subSet("b", "e")); //[b, c, d]

        //对返回的视图的操作会直接影响到原来的Set
        set.subSet("b", "e").clear(); //会从原set中删除
        System.out.println(set); //[a, f]
    }

    @Test
    public void tesNavigableSet() {
        NavigableSet<String> set = new TreeSet<String>();
        set.addAll(Arrays.asList(new String[]{
                "c", "a", "b", "d","f"
        }));
        System.out.println(set.floor("a")); //a
        System.out.println(set.lower("b")); //a
        System.out.println(set.ceiling("d"));//d
        System.out.println(set.higher("c"));//d
        System.out.println(set.subSet("b", true, "d", true)); //[b, c, d]
        System.out.println(set.pollFirst()); //a
        System.out.println(set.pollLast()); //f
        System.out.println(set.descendingSet()); //[d, c, b]
    }
}
