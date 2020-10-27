package com.data.concurrent.a_create;

/*
 * @Author: tianyong
 * @Date: 2020/10/23 14:49
 * @Description: 第二种方式 (实现Runnable接口)
 */
public class ImplRunnable implements Runnable {


    @Override
    public void run() {
        for(int i=0;i<50;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
    }
    public static void main(String[] args) {
        for(int i=0;i<50;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
            if(i==20){
                ImplRunnable sr=new ImplRunnable();
                new Thread(sr,"thread-1").start();
                new Thread(sr,"thread-2").start();
            }
        }
    }



}
