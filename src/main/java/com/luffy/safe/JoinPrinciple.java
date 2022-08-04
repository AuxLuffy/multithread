package com.luffy.safe;

/**
 * 通过理解join原理，分析出join的代替写法
 *
 * @author sunzhangfei
 * @since 2022/7/29 4:26 下午
 */
public class JoinPrinciple {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("进入了子线程");
                try {
//                    System.out.println("打断主线程");
//                    mainThread.interrupt();
                    Thread.sleep(5000);
                    System.out.println("子线程运行结束");
                } catch (InterruptedException e) {
                    System.out.println("子线程被中断了");
                    e.printStackTrace();
                }
            }
        }) {

        };

        t1.start();
        try {
//            t1.join();
            // 等价代码就是用t1当锁把当前线程进入wai状态,当t1退出的时候会调用t1的notifyAll唤醒被它上锁进入等待的所有线程
            synchronized (t1) {
                t1.wait();
            }
            System.out.println("主线程被唤醒了");
        } catch (InterruptedException e) {
            System.out.println("主线程被中断了");
            e.printStackTrace();
            //当主线程被中断时最好把中断传递给所有子线程，否则子线程会继续运行
            t1.interrupt();
        }

    }
}
