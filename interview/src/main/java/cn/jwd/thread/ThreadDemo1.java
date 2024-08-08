package cn.jwd.thread;

import java.util.concurrent.*;

public class ThreadDemo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*
        继承Thread实现多线程
        MyThread t1 = new MyThread();
        t1.start();
        MyThread t2 = new MyThread();
        t2.start();
         */

        /*
        实现Runnable接口实现多线程
        MyRunnable r1 = new MyRunnable();
        Thread t1 = new Thread(r1);
        t1.start();
        Thread t2=new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println("线程2:"+i);
            }
        });
        t2.start();
         */

        /*
        通过线程池实现多线程
                ExecutorService service = Executors.newFixedThreadPool(10);
//        创建三个线程
        for (int i = 0; i < 3; i++) {
            service.execute(()->{
                for (int j = 0; j < 100; j++) {
                    System.out.println(Thread.currentThread().getName()+":"+j);
                }
            });
        }
        Future<?> submit = service.submit(() -> "线程执行完毕");
        try {
            System.out.println(submit.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
         */

        /*
        通过callable实现多线程
        MyCallable callable = new MyCallable();
        FutureTask<String> task = new FutureTask<>(callable);
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        String s = task.get();
        System.out.println(s);
         */
    }
}
