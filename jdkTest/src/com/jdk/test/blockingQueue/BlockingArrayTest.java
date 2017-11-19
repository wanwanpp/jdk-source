package com.jdk.test.blockingQueue;

import java.util.concurrent.atomic.AtomicInteger;

//控制台的打印顺序有可能不对，1000可能打印在了中间。
public class BlockingArrayTest {
    public static void main(String[] args){  
        final BlockingArray<Integer> blockingArray = new BlockingArray<Integer>(10);
          
//        final BlockingArrayWithCondition<Integer> blockingArray = new BlockingArrayWithCondition<Integer>(10);

        final AtomicInteger count = new AtomicInteger(0);
          
        for(int i = 0; i < 1000; i ++){
            Thread t = new Thread(new Runnable(){  
      
                @Override  
                public void run() {  
                    try {  
                        blockingArray.put(count.incrementAndGet());  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
                  
            });  
            t.start();  
        }  
          
        for(int i = 0; i < 1000; i ++){
            Thread t = new Thread(new Runnable(){  
      
                @Override  
                public void run() {  
                    try {  
                        blockingArray.take();  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
                  
            });  
            t.start();  
        }  
          
    }  
}  