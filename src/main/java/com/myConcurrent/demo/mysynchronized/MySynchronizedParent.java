package com.myConcurrent.demo.mysynchronized;

public abstract class MySynchronizedParent {

    public synchronized void doSomething() {
        System.out.println("Parent say hello");
    }
}
