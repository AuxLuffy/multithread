package com.luffy.cacl.uncaughtexception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * desc: 自定义的线程异常处理器
 *
 * @author luffy
 * @since 2022/7/29 23:20
 */
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private String name;

    public MyUncaughtExceptionHandler(String name) {
        this.name = name;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger log = Logger.getAnonymousLogger();
        log.log(Level.WARNING, "线程异常终止了" + t.getName(), e);
        System.out.println(name + "捕获了异常");
    }
}
