package cn.jwd.thread;

public class MyTestThread2 implements Runnable {
    private int count = 0;

    public void run() {
        while (true) {
            count++;
            System.out.println(Thread.currentThread().getName() + ":" + count);
        }
    }
}
