package com.luffy.syncnzd;

public class SyncDemo {

    int i = 0;

    public synchronized void method1() {
        i++;
    }
//
//    static int sum;
//
//    public static void method2() {
//        sum++;
//    }
//
//    Object lock = new Object();
//
//    public void method3() {
//        synchronized (lock) {
//            i++;
//        }
//    }
}