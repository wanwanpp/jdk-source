package com.jdk.test;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.CharBuffer;

/**
 * @author 王萍
 * @date 2018/1/8 0008
 */
public class TestBuffer {

    public static void main(String[] args) throws IOException {
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put('H');
        buffer.put('E');
        buffer.put('L');
        buffer.put('L');
        buffer.put('O');

        print(buffer);
        buffer.flip();
        print(buffer);

        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        print(buffer);
        System.out.println(buffer.get(2));
        print(buffer);

//        创建一个视图缓冲区，与原始缓冲区共享数据元素。操作会影响原始缓冲区。
        buffer.duplicate();
    }

    public static void print(Buffer buffer) {
        System.out.println("position : " + buffer.position() + " limit : " + buffer.limit() + " capacity : " + buffer.capacity());
    }
}
