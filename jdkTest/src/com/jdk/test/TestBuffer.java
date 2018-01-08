package com.jdk.test;

import java.nio.CharBuffer;

/**
 * @author 王萍
 * @date 2018/1/8 0008
 */
public class TestBuffer {

    public static void main(String[] args) {
        CharBuffer buffer = CharBuffer.allocate(5);
        buffer.put('H');
        buffer.put('E');
        buffer.put('L');
        buffer.put('L');
        buffer.put('O');

        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
    }
}
