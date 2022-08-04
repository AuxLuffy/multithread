package com.luffy.cacl.uncaughtexception;

/**
 * desc: 使用自定义的异常处理器
 *
 * @author luffy
 * @since 2022/7/29 23:25
 */
public class UseMyUncaughtExceptionHandler implements Runnable {
    public static void main(String[] args) throws InterruptedException {

        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler("我"));
        Thread t1 = new Thread(new UseMyUncaughtExceptionHandler(), "MyThread-1");
        Thread t2 = new Thread(new UseMyUncaughtExceptionHandler(), "MyThread-2");
        Thread t3 = new Thread(new UseMyUncaughtExceptionHandler(), "MyThread-3");
        Thread t4 = new Thread(new UseMyUncaughtExceptionHandler(), "MyThread-4");
        t1.start();
        Thread.sleep(300);
        t2.start();
        Thread.sleep(300);
        t3.start();
        Thread.sleep(300);
        t4.start();


    }

    @Override
    public void run() {
        throw new RuntimeException();
    }
}
