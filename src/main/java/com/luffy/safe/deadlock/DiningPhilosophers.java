package com.luffy.safe.deadlock;

/**
 * desc: 哲学家就餐问题导致的死锁（五个人围一桌吃饭，有五只筷子，科学家思考一段时间吃饭时必须先拿起左侧筷子再拿到右侧筷子才能吃饭）
 * 解决方案：
 * 1。 服务员检查（避免策略）
 * 2。 改变一个哲学家拿叉子的顺序（避免策略）
 * 3。 餐票（避免策略），只发四张票
 * 4。 领导调节（检测与恢复策略）
 *
 * @author luffy
 * @since 2022/8/6 0:41
 */
public class DiningPhilosophers {
    public static class Philosopher implements Runnable {

        private Object leftChopstick, rightChopstick;

        public Philosopher(Object leftChopstick, Object rightChopstick) {
            this.leftChopstick = leftChopstick;
            this.rightChopstick = rightChopstick;
        }

        @Override
        public void run() {
            try {

                while (true) {
                    doAction("thinking");
                    synchronized (leftChopstick) {
                        doAction("pick up left chopstick");
                        synchronized (rightChopstick) {
                            doAction("pick up right chopstick");
                            doAction("eating");
                            doAction("put down right chopstick");
                        }
                        doAction("put down left chopstick");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void doAction(String action) throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " " + action);
            Thread.sleep((long) (Math.random() * 10));
        }
    }


    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[5];
        Object[] chopsticks = new Object[philosophers.length];
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Object();
        }
        for (int i = 0; i < philosophers.length; i++) {
            Object leftChopstick = chopsticks[i];
            Object rightChopstick = chopsticks[(i + 1) % chopsticks.length];
            if (i == philosophers.length - 1) {
                philosophers[i] = new Philosopher(leftChopstick, rightChopstick);
            } else {
                philosophers[i] = new Philosopher(leftChopstick, rightChopstick);
            }
            new Thread(philosophers[i], "哲学家" + (i + 1) + "号").start();
        }
    }
}
