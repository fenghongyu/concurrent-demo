package com.myConcurrent.demo.mysynchronized;

public class MySynchronizedChild extends MySynchronizedParent {

    @Override
    public synchronized void doSomething() {
        System.out.println("Child say hello");
        super.doSomething();
    }

    public void sayBy() {
        synchronized (this){
            System.out.println("Child say by 01");
            synchronized (this){
                System.out.println("Child say by 02");
            }
        }
    }

    public static void main(String[] args) {
        MySynchronizedChild child = new MySynchronizedChild();
        child.doSomething(); // 01
        child.sayBy(); // 02
    }
}
