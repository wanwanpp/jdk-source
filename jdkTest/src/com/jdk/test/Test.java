package com.jdk.test;

/**
 * Created by 王萍 on 2017/10/26 0026.
 */
public class Test {

    public static void main(String[] args) {

        int COUNT_BITS=29;

        int CAPACITY = (1 << 29) - 1;
        System.out.println(Integer.toBinaryString(CAPACITY).length());
        System.out.println(Integer.toBinaryString(~CAPACITY));


        final int RUNNING = -1 << COUNT_BITS;
        final int SHUTDOWN = 0 << COUNT_BITS;
        final int STOP = 1 << COUNT_BITS;
        final int TIDYING = 2 << COUNT_BITS;
        final int TERMINATED = 3 << COUNT_BITS;
        System.out.println("running: "+Integer.toBinaryString(RUNNING));
        System.out.println("SHUTDOWN: "+Integer.toBinaryString(SHUTDOWN));
        System.out.println("STOP: "+Integer.toBinaryString(STOP));
        System.out.println("TIDYING: "+Integer.toBinaryString(TIDYING));
        System.out.println("TERMINATED: "+Integer.toBinaryString(TERMINATED));

        System.out.println("running is less than shutdown : "+(RUNNING<SHUTDOWN));
        System.out.println("STOP is less than shutdown : "+(STOP<SHUTDOWN));
        System.out.println("TIDYING is less than shutdown : "+(TIDYING<SHUTDOWN));
        System.out.println("TERMINATED is less than shutdown : "+(TERMINATED<SHUTDOWN));

    }
}
