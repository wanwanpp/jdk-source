package com.jdk.test;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Created by 王萍 on 2017/11/18 0018.
 */

public class TestAtomicReferenceFieldUpdate {

    private static final AtomicReferenceFieldUpdater<Node, Node> NEXT_UPDATE = AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class, "next");

    public static void main(String[] args) {
        Node<Integer> integerNode = new Node<Integer>(3);
        Node<Integer> integerNodeNext = new Node<>(4);
        integerNode.next = integerNodeNext;
        NEXT_UPDATE.compareAndSet(integerNode, integerNodeNext, new Node(5));
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
