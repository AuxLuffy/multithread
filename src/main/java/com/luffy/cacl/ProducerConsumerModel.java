package com.luffy.cacl;

import java.util.Date;
import java.util.LinkedList;

/**
 * desc: 生产者消费者模型
 *
 * @author luffy
 * @since 2022/7/29 1:00
 */
public class ProducerConsumerModel {
    public static void main(String[] args) {
        EventStorage storage = new EventStorage();
        Producer producer = new Producer(storage);
        Thread producerThread = new Thread(producer);
        Consumer consumer = new Consumer(storage);
        Thread consumerThread = new Thread(consumer);
        producerThread.start();
        consumerThread.start();
    }
}

class Producer implements Runnable {

    private IStorage storage;

    public Producer(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            storage.put();
        }
    }
}

class Consumer implements Runnable {

    private IStorage storage;

    public Consumer(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            storage.take();
        }
    }
}

class EventStorage implements IStorage {
    private int maxSize;
    private LinkedList<Date> queue;


    EventStorage() {
        this.maxSize = 10;
        this.queue = new LinkedList<>();
    }


    @Override
    public void put() {
        synchronized (this) {
            while (queue.size() >= maxSize) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.add(new Date());
            System.out.println("当前队列中有" + queue.size() + "个任务");
            notify();
        }
        try {
            Thread.sleep((long) (Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void take() {
        synchronized (this) {
            while (queue.size() <= 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("取到任务：" + queue.poll() + "，队列中还有：" + queue.size());
            notify();
        }

        try {
            Thread.sleep((long) (Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

interface IStorage {
    /**
     * 添加任务
     */
    void put();

    /**
     * 取任务
     */
    void take();
}
