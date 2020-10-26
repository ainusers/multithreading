package com.data.concurrent.control;

import com.data.concurrent.begin.ExtThread;
import com.data.concurrent.begin.ImplRunnable;

/*
 * @Author: tianyong
 * @Date: 2020/10/23 15:49
 * @Description: 睡眠线程 (sleep()在哪儿执行,执行哪儿的线程)
 */
public class SleepThread implements Runnable{


    @Override
    public void run() {
        for(int i=0;i<50;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
    }
    public static void main(String args[]) throws InterruptedException {
        for(int i=0;i<50;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
            if(i==20){
                ImplRunnable sr=new ImplRunnable();
                new Thread(sr,"thread-1").start();
                Thread.sleep(1);
            }
        }
    }

}
