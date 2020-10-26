package com.data.concurrent.control;

/*
 * @Author: tianyong
 * @Date: 2020/10/23 15:29
 * @Description: 守护线程 （只要主线程结束,无论守护线程是否执行完,一定会结束）
 */
public class DaemonThread implements Runnable{

    @Override
    public void run() {
        for(int i=0;i<1000;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
    }
    public static void main(String[] args) {
        for(int i=0;i<100;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
            if(i==20){
                DaemonThread tdt=new DaemonThread();
                Thread thread=new Thread(tdt);
                // 设置为后台线程，则前台线程都死亡，这个线程会自动死亡，必须放在start之前
                thread.setDaemon(true);
                thread.start();
            }
        }
    }

}
