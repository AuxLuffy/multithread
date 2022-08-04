package com.luffy.safe.multierror.oom;

/**
 * 构造导致的发布溢出
 *
 * @author sunzhangfei
 * @since 2022/8/1 4:35 下午
 */
public class ConstructorOutError {
    static Point sPoint;

    public static void main(String[] args) throws InterruptedException {
        new PointMaker().start();
        Thread.sleep(10);
        if (sPoint != null) {
            System.out.println(sPoint.toString());
        }
    }
}

class Point {
    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        ConstructorOutError.sPoint = this;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class PointMaker extends Thread {
    @Override
    public void run() {
        try {
            new Point(1, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}