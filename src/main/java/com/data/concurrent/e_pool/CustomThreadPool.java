package com.data.concurrent.e_pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * @Author: tianyong
 * @Date: 2020/10/26 17:04
 * @Description: 自定义线程池
 */
public class CustomThreadPool {

    // 工作队列存储
    LinkedBlockingQueue<Runnable> workQueue=new LinkedBlockingQueue<>(100);

    // 主函数入口
    public static void main(String[] args) {
        new CustomThreadPool().threadPool();
    }


    public void threadPool(){
        ThreadFactory threadFactory=new ThreadFactory() {
            AtomicInteger atomic=new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread=new Thread(r);
                thread.setName("MyThread"+atomic.getAndIncrement());
                return thread;
            }
        };
        /**
         * corePoolSize核心池最大数量
         * maximumPoolSize最大线程池上限个数
         * keepAliveTime任务执行完，销毁线程的延时
         * unit时间单位   TimeUnit.SECONDS;
         * workQueue 用于储存任务的工作队列
         * threadFactory
         */
        ThreadPoolExecutor pool=new ThreadPoolExecutor(5, 10, 1, TimeUnit.SECONDS, workQueue, threadFactory);
        for(int i=0;i<50;i++){
            pool.execute(new Runnable() {
                public void run() {
                    method();
                }
            });
        }
    }


    private void method(){
        System.out.println("ThreadName:"+Thread.currentThread().getName()+"进来了");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ThreadName:"+Thread.currentThread().getName()+"出去了");
    }


}
