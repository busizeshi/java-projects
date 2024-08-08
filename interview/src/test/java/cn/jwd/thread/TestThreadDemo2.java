package cn.jwd.thread;

import org.junit.Test;

public class TestThreadDemo2 {
    @Test
    public void test1() {
        MyTestThread1 myTestThread1 = new MyTestThread1();
        myTestThread1.start();
    }

    @Test
    public void test2() throws InterruptedException {
        MyTestThread2 myTestThread2 = new MyTestThread2();
        Thread thread = new Thread(myTestThread2);
        thread.start();
        Thread.sleep(3000);
        thread.interrupt();
    }
}
