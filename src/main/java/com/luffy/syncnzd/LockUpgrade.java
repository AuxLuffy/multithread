package com.luffy.syncnzd;


import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @author luffy
 * @since 2023/8/3 2:22
 */
public class LockUpgrade {
    public static void main(String[] args) throws InterruptedException {
        User userTemp = new User();
        System.out.println("无状态（001）：" + ClassLayout.parseInstance(userTemp).toPrintable());
        //jvm默认4秒延迟后开启偏向锁，可通过 -XX:BiasedLockingStartupDelay=0取消延时；如果不需要偏向销可以通过 -XX:-UseBiasedLocking=false
        TimeUnit.SECONDS.sleep(5);
        User user= new User();
        System.out.println("启用偏向锁（101）：" + ClassLayout.parseInstance(user).toPrintable());//只有启用偏向锁（其中的线程 id为空）才能升级为偏向锁
        for (int i = 0; i < 2; i++) {
            synchronized (user){
                System.out.println("偏向锁（101）(带线程id)：" + ClassLayout.parseInstance(user).toPrintable());
            }
            System.out.println("偏向锁头释放（101）(带线程id)：" + ClassLayout.parseInstance(user).toPrintable());
        }
        new Thread(()->{
            synchronized (user){
                System.out.println("轻量锁（00）：" + ClassLayout.parseInstance(user).toPrintable());//当有其他线程 使用当前锁的时候立马上升级到轻量锁
                try {
                    System.out.println("睡眠3秒钟================");
                    Thread.sleep(3000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("轻量-->重量（10）" + ClassLayout.parseInstance(user).toPrintable());//当有竞争的时候就升级成重量级锁
            }
        }).start();
        Thread.sleep(1000);
        new Thread(()->{
            synchronized (user){
                System.out.println("重量级锁（10）：" + ClassLayout.parseInstance(user).toPrintable());
            }
        }).start();
    }
}

