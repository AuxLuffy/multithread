package com.luffy.safe.deadlock;

import java.util.Random;

/**
 * 多人同时转帐
 *
 * @author sunzhangfei
 * @since 2022/8/5 4:32 下午
 */
public class MultiTransferMoney {
    /**
     * 帐户数
     */
    private static final int NUM_ACCOUNT = 500;
    /**
     * 具体的帐户数据
     */
    private static final TransferMoney.Account[] ACCOUNTS = new TransferMoney.Account[NUM_ACCOUNT];
    /**
     * 每个人第次转的
     */
    private static final int PER_NUM_PERSON = 1000000;
    private static final int INIT_MONEY = 1000;
    private static final int NUM_THREAD = 50;


    public static void main(String[] args) {
        for (int i = 0; i < NUM_ACCOUNT; i++) {
            TransferMoney.Account account = new TransferMoney.Account("工商银行户" + i, INIT_MONEY);
            ACCOUNTS[i] = account;
        }
        Random rdm = new Random();
        class TransferThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < PER_NUM_PERSON; i++) {
                    int money = rdm.nextInt(INIT_MONEY);
                    int from = rdm.nextInt(NUM_ACCOUNT);
                    int to = rdm.nextInt(NUM_ACCOUNT);
//                    if (from == to) {
//                        System.out.println("转帐的两个人是同一个人，停止转帐");
//                        return;
//                    }else
                    {
                        TransferMoney.transferMoney(ACCOUNTS[from], ACCOUNTS[to], money);
                    }
                }
            }
        }
        for (int i = 0; i < NUM_THREAD; i++) {
            TransferThread transferThread = new TransferThread();
            transferThread.start();
        }
    }
}
