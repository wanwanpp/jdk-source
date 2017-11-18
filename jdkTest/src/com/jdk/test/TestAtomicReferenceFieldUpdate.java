package com.jdk.test;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Created by 王萍 on 2017/11/18 0018.
 */
//利用AtomicReferenceFieldUpdater对Node类的volatile修饰的next属性进行了包装，使其具有CAS操作。
public class TestAtomicReferenceFieldUpdate {

    private static AtomicReferenceFieldUpdater<Node, Node> nextUpdate = AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class, "next");

    public static void main(String[] args) {
        Node<Integer> integerNode = new Node<Integer>(3);
        Node<Integer> integerNodeNext = new Node<>(4);
        integerNode.next = integerNodeNext;
        nextUpdate.compareAndSet(integerNode, integerNodeNext, new Node(5));
        System.out.println(integerNode.next.item);
    }
}

class Node<E> {
    public final E item;
    public volatile Node<E> next;

    public Node(E item) {
        this.item = item;
    }
}
