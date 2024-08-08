package cn.jwd.thread;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    public String call() throws Exception {
        System.out.println("call()方法被执行");
        return "Hello World";
    }
}
