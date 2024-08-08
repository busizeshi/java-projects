package cn.jwd.thread;

public class MyTestThread1 extends Thread {
    private volatile boolean flag = false;
    private int i = 0;

    @Override
    public void run() {
        while (!flag) {
            System.out.println("线程" + Thread.currentThread().getName() + "正在运行");
            i++;
            if (i == 5) {
                flag = true;
            }
        }
    }
}
