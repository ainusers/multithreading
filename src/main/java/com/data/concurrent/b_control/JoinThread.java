package com.data.concurrent.b_control;

/*
 * @Author: tianyong
 * @Date: 2020/10/23 15:23
 * @Description: 阻塞线程 (在主线程执行过程中,插入必须执行的程序,待插入程序执行完或到指定时间，主线程继续执行)
 */
public class JoinThread implements Runnable{


    @Override
    public void run() {
        for(int i=0;i<50;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<50;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
            if(i==20){
                JoinThread tj=new JoinThread();
                Thread thread=new Thread(tj);
                thread.start();
                // 放在start之后，不然有问题
                thread.join();
            }
        }
    }


}
