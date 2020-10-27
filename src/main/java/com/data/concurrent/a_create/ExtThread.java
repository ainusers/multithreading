package com.data.concurrent.a_create;

/*
 * @Author: tianyong
 * @Date: 2020/10/23 14:36
 * @Description: 第一种方式 (extends Thread)
 */
public class ExtThread extends Thread{


    @Override
    public void run() {
        for(int i=0;i<50;i++){
            System.out.println(getName()+" "+i);
        }
    }
    public static void main(String[] args) {
        for(int i=0;i<50;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
            if(i==20){
                new ExtThread().start();
                new ExtThread().start();
            }
        }
    }



}
