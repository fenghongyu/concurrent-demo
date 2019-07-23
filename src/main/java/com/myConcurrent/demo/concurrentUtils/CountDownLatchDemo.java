package com.myConcurrent.demo.concurrentUtils;

import java.util.concurrent.CountDownLatch;

/**
 * @author fenghongyu
 * 如何测试N个线程并发执行某个任务需要的时间？
 */
public class CountDownLatchDemo {

    public void getRunTime(){
        //线程准备阶段
        int threadNum = 3;
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(threadNum);
        for(int i=0;i<threadNum;i++){
            new Thread(() -> {
                try {
                    startGate.await(); //启动门等待计数器达到0，如果一直不到0，则一直等待。
                    calcution();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    endGate.countDown(); //结束门计数减一，表示该任务已准备就绪，等待开始执行。
                }
            }).start();
        }
        //线程准备完毕，准备启动
        long startTime = System.nanoTime();
        startGate.countDown(); //启动门计数减1，使得计数器达到0，启动线程
        try {
            endGate.await(); // 主线程，等待所有线程都执行完 endGate.countDown();使计数器达到0，表示所有线程都计算完毕。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();

        System.out.println(endTime-startTime);
    }

    //某个计算任务
    private void calcution() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CountDownLatchDemo demo = new CountDownLatchDemo();
        demo.getRunTime();
    }
}
