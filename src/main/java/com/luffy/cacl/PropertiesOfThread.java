package com.luffy.cacl;

/**
 * desc: 线程的相关属性
 *
 * @author luffy
 * @since 2022/7/29 22:18
 */
public class PropertiesOfThread {
    public static void main(String[] args) throws InterruptedException {
        Thread main = Thread.currentThread();
        System.out.println("主线程名字：" + main.getName() + ", 线程id: " + main.getId());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "子线程运行开始");
                try {
                    Thread.sleep(500);
                    System.out.println("子线程运行结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "自定义线程1");
        t.start();
        Thread.sleep(200);
        t.setName("lasld");
        System.out.println("新线程名字：" + t.getName() + ", 线程id: " + t.getId());
    }
}
