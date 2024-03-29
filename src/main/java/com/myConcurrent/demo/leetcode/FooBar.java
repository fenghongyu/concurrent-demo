package com.myConcurrent.demo.leetcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 我们提供一个类：

 class FooBar {
 public void foo() {
     for (int i = 0; i < n; i++) {
       print("foo");
     }
 }

 public void bar() {
     for (int i = 0; i < n; i++) {
       print("bar");
     }
 }
 }
 两个不同的线程将会共用一个 FooBar 实例。其中一个线程将会调用 foo() 方法，另一个线程将会调用 bar() 方法。

 请设计修改程序，以确保 "foobar" 被输出 n 次。

  

 示例 1:

 输入: n = 1
 输出: "foobar"
 解释: 这里有两个线程被异步启动。其中一个调用 foo() 方法, 另一个调用 bar() 方法，"foobar" 将被输出一次。
 示例 2:

 输入: n = 2
 输出: "foobarfoobar"
 解释: "foobar" 将被输出两次。

 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/print-foobar-alternately
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

 * @author fenghongyu
 */
public class FooBar {
    private int n;
    private volatile int status = 0;

    private ReentrantLock lock = new ReentrantLock();
    Condition fooCondition = lock.newCondition();
    Condition barCondition = lock.newCondition();

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                if(status != 0) {
                    fooCondition.await();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                status=1;
                barCondition.signalAll();

            } catch (Exception e){

            } finally {
                lock.unlock();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                if(status != 1) {
                    barCondition.await();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                status = 0;
                fooCondition.signalAll();
            } catch (Exception e){

            } finally {
                lock.unlock();
            }

        }
    }
}
