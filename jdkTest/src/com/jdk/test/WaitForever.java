package com.jdk.test;

/**
 * @author 王萍
 * @date 2017/12/18 0018
 * 验证wait(3000)方法调用后，3秒过去了不会立即执行，除了cpu资源外，还需要等待锁资源。
 */
public class WaitForever {

    public static Object monitor = new Object();
    static long start;

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("马上执行wait（3000）方法");
                synchronized (monitor) {
                    try {
                        start = System.currentTimeMillis();
                        monitor.wait(3000);
                        System.out.println("wait方法结束了");
                        System.out.println("等待了" + (System.currentTimeMillis() - start) / 1000 + "秒。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Thread.sleep(100);
//        模拟锁资源被占用。
        synchronized (monitor) {
            Thread.sleep(500000);
        }
    }
}
