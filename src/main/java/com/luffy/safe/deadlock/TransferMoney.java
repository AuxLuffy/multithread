package com.luffy.safe.deadlock;

/**
 * 转帐时候遇到的死锁，一旦打开注释便会发生死锁
 *
 * @author sunzhangfei
 * @since 2022/8/5 4:13 下午
 */
public class TransferMoney implements Runnable {
    static Account a, b;

    static {
        a = new Account("A", 500);
        b = new Account("B", 500);
    }

    int flag = 1;

    public static void main(String[] args) throws InterruptedException {
        TransferMoney r1 = new TransferMoney();
        r1.flag = 1;

        TransferMoney r2 = new TransferMoney();
        r2.flag = 0;

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(a.name + "的余额为：" + a.ballance);
        System.out.println(b.name + "的余额为：" + b.ballance);
    }

    @Override
    public void run() {
        if (flag == 1) {
            transferMoney(a, b, 200);
        }
        if (flag == 0) {
            transferMoney(b, a, 200);
        }
    }

    public static final void transferMoney(Account from, Account to, int money) {
        synchronized (from) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            synchronized (to) {
                if (from.ballance - money < 0) {
                    System.out.println(from.name + "给" + to.name + "转帐，余额不足，转帐失败。");
                }
                from.ballance -= money;
                to.ballance += money;
                System.out.println(from.name + "成功转帐" + to.name + ": " + money + "元");
            }
        }
    }

    static class Account {

        public Account(String name, int ballance) {
            this.name = name;
            this.ballance = ballance;
        }

        final String name;
        int ballance;
    }
}
