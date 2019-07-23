package com.myConcurrent.demo.concurrentUtils;

import java.util.concurrent.Semaphore;

/**
 *  控制线程的并发连接数
 * @author fenghongyu
 */
public class SemaphoreDemo {

    public void controlConnectionNum() {
        int maxConnectionNum = 10;
        int threadNum = 30;
        Semaphore semaphore = new Semaphore(maxConnectionNum);

        for(int i=0;i<threadNum;i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("--------connection to mysql");
                    Thread.sleep(1000);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        SemaphoreDemo demo = new SemaphoreDemo();
        demo.controlConnectionNum();
    }
}
