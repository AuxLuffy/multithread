package com.luffy.cacl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * desc: 使用Volatile关键字错误停止线程的示例
 *
 * @author luffy
 * @since 2022/7/27 22:48
 */
public class WrongWayVolatileCantStop {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue storage = new ArrayBlockingQueue(10);
        Producer producer = new Producer(storage);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Thread.sleep(1000);
        Consumer consumer = new Consumer(storage);
        while (consumer.canConsume()) {
            consumer.consume();
        }
        System.out.println("消费不了更多数据了");
        producerThread.interrupt();
//        producer.isCancel = true;

    }

    private static class Producer implements Runnable {
        public volatile boolean isCancel = false;

        private ArrayBlockingQueue storage;

        public Producer(ArrayBlockingQueue storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            int num = 0;

            try {
                while (num < 10000 && !Thread.currentThread().isInterrupted()) {
                    if (num % 100 == 0) {
                        storage.put(num);
                        System.out.println(num + "是100的倍数，放入仓库了");
                    }
                    num++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("生产者停止运行");
            }
        }
    }

    static class Consumer {
        private BlockingQueue storage;

        public Consumer(BlockingQueue storage) {
            this.storage = storage;
        }

        public boolean canConsume() {
            if (Math.random() > 0.95) {
                return false;
            }
            return true;
        }

        public void consume() throws InterruptedException {
            System.out.println(storage.take() + "被消费了");
            Thread.sleep(100);
        }

    }
}
