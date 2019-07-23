package com.myConcurrent.demo.mysynchronized;

/**
 * Java的监视器模式-使用sychronized锁住私有对象
 * @author fenghongyu
 */
public class PrivateLock {
    private Object object = new Object();

    private Integer value;

    public void addValue(Integer val) {
        synchronized (object){
            value +=val;
        }
    }
}
