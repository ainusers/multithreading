package com.data.concurrent.begin;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
 * @Author: tianyong
 * @Date: 2020/10/23 15:02
 * @Description: 第三种方式 (Lambda表达式创建Callable对象)
 */
public class FutureThread {

    public static void main(String[] args) {
        FutureTask<Integer>task=new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int i=0;
                for(;i<100;i++){
                    System.out.println(Thread.currentThread().getName()+" "+i);
                }
                return i;
            }

        });
        for(int i=0;i<100;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
            if(i==20){
                new Thread(task,"return").start();
            }
        }
        try {
            System.out.println("子线程的返回值"+task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
