package com.luffy.safe.jmm;

/**
 * 演示可见性带来的问题
 * 一个线程对某个共享变量的修改并不会第一时间写入到主存中，其他线程读到的共享变量的值很可能不是修改后的值
 *
 * @author sunzhangfei
 * @since 2022/8/2 4:42 下午
 */
public class FieldVisibility {
    volatile int a = 1;
    volatile int b = 2;

    public static void main(String[] args) {
        while (true) {
            FieldVisibility test = new FieldVisibility();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2);//休眠一秒可能不能达到效果，更改休眠时间能更容易看到想要的碰撞结果
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.change();
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.print();
                }
            }).start();
        }
    }

    private void change() {
        a = 3;
        b = a;
    }

    private void print() {
        System.out.println("a=" + a + ", b=" + b);
    }

}
