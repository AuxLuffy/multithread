package com.luffy.cacl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * desc:
 *
 * @author luffy
 * @since 2022/7/29 22:59
 */
public class CantCatchDirectly implements Runnable {
    static Logger log = LoggerFactory.getLogger(CantCatchDirectly.class);

    public static void main(String[] args) {
        try {

            Thread.UncaughtExceptionHandler exceptionHandler = new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    log.debug("uncaughtException", e);
                }
            };
            Thread t1 = new Thread(new CantCatchDirectly(), "MyThread-1");
            Thread t2 = new Thread(new CantCatchDirectly(), "MyThread-2");
            Thread t3 = new Thread(new CantCatchDirectly(), "MyThread-3");
            Thread t4 = new Thread(new CantCatchDirectly(), "MyThread-4");
            t1.setUncaughtExceptionHandler(exceptionHandler);
            t2.setUncaughtExceptionHandler(exceptionHandler);
            t3.setUncaughtExceptionHandler(exceptionHandler);
            t4.setUncaughtExceptionHandler(exceptionHandler);
            log.debug("开始");
            t1.start();
            Thread.sleep(300);
            t2.start();
            Thread.sleep(300);
            t3.start();
            Thread.sleep(300);
            t4.start();


        } catch (RuntimeException | InterruptedException e) {
            System.out.println("Exception Caught!");
        }
    }

    @Override
    public void run() {
        throw new RuntimeException();
    }
}
