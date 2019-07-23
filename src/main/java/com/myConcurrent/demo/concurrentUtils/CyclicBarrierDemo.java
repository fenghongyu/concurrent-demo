package com.myConcurrent.demo.concurrentUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 关于线程的同步屏障控制-CyclicBarrier
 * @author fenghongyu
 */
public class CyclicBarrierDemo {

    public void getRunTime(){
        //线程准备阶段
        int threadNum = 3;
        //这里 + 1， 是为了让主线程控制其他被屏障挡住的线程启动。
        CyclicBarrier startCyclicBarrier = new CyclicBarrier(threadNum + 1); //线程启动屏障
        CyclicBarrier endCyclicBarrier = new CyclicBarrier(threadNum + 1);  //线程终止屏障
        for(int i=0;i<threadNum;i++) {
            new Thread(() -> {
                try {
                    //当前线程已到达屏障
                    startCyclicBarrier.await();
                    calcution(); //任务计算
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        endCyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        //线程准备完毕，准备启动
        long startTime = System.nanoTime();
        try {
            startCyclicBarrier.await(); //主线程到达屏障，使得所有线程均到达屏障，启动其他等待线程
            endCyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
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
        CyclicBarrierDemo cyclicBarrierDemo = new CyclicBarrierDemo();
        cyclicBarrierDemo.getRunTime();
    }
}
