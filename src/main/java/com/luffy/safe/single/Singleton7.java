package com.luffy.safe.single;

/**
 * desc: 静态内部类方式的单例
 *
 * @author luffy
 * @since 2022/8/5 2:32
 */
public class Singleton7 {
    private Singleton7() {

    }

    private static class Subject {
        private static final Singleton7 INSTANCE = new Singleton7();
    }

    public static Singleton7 getInstance() {
        return Subject.INSTANCE;
    }
}
