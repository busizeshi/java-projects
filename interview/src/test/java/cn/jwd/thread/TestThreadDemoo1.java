package cn.jwd.thread;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

//线程执行顺序
public class TestThreadDemoo1 {

    static Object lock = new Object();

    private static final int PARTIES = 3; // 参与线程数
    private static final int ROUNDS = 5; // 重复次数

    private static final Semaphore alaskaSemaphore = new Semaphore(0);
    private static final Semaphore huskySemaphore = new Semaphore(0);
    private static final Semaphore samoyedSemaphore = new Semaphore(0);

    static {
        // 初始化时，只允许阿拉斯加线程开始
        alaskaSemaphore.release();
    }

    @Test
    public void test1() {
        Thread t1 = new Thread(() -> {
            System.out.print("嘎嘎");
        });
        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.print("咕咕");
        });
        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.print("哈哈");
        });
        t1.start();
        t2.start();
        t3.start();
    }

    @Test
    public void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.print("阿拉斯加 ");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.print("阿拉斯加执行完成");
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.print("哈士奇 ");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.print("哈士奇执行完成");
            }
        });
        Thread t3 = new Thread(() -> {
            synchronized (lock) {
                System.out.print("萨摩耶 ");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.print("萨摩耶执行完成");
            }
        });
        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(1000);

//        synchronized (lock) {
//            lock.notify();
//        }
        synchronized (lock){
            lock.notifyAll();
        }
    }

    @Test
    public void test3() {
        CyclicBarrier barrier = new CyclicBarrier(PARTIES, () -> {
            // 这里是屏障动作，但在这个案例中我们不需要做任何事
            // 因为它仅仅是为了让所有线程都到达这里后才继续
        });

        // 创建并启动线程
        Thread alaska = new Thread(() -> {
            for (int i = 0; i < ROUNDS; i++) {
                System.out.print("阿拉斯加");
                try {
                    barrier.await(); // 等待其他线程
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread husky = new Thread(() -> {
            for (int i = 0; i < ROUNDS; i++) {
                try {
                    barrier.await(); // 等待阿拉斯加
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.print("哈士奇");
                try {
                    barrier.await(); // 等待萨摩耶
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread samoyed = new Thread(() -> {
            for (int i = 0; i < ROUNDS; i++) {
                try {
                    barrier.await(); // 等待哈士奇
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("萨摩耶"); // 使用println来换行
            }
        });

        alaska.start();
        husky.start();
        samoyed.start();
    }

    @Test
    public void test4() throws InterruptedException {
        Thread alaska = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    alaskaSemaphore.acquire(); // 阿拉斯加线程总是可以获取许可
                    System.out.print("阿拉斯加");
                    huskySemaphore.release(); // 释放哈士奇线程的许可
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread husky = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    huskySemaphore.acquire(); // 等待阿拉斯加的许可
                    System.out.print("哈士奇");
                    samoyedSemaphore.release(); // 释放萨摩耶线程的许可
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread samoyed = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    samoyedSemaphore.acquire(); // 等待哈士奇的许可
                    System.out.println("萨摩耶"); // 使用println来换行
                    alaskaSemaphore.release(); // 释放阿拉斯加的许可，为下一轮做准备
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        alaska.start();
        husky.start();
        samoyed.start();
    }
}//  1 3 4 6 7 9