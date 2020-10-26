package com.data.concurrent.demo;

import java.util.concurrent.locks.ReentrantLock;

/*
 * @Author: tianyong
 * @Date: 2020/10/23 16:11
 * @Description: ReentrantLock (取款问题)
 */
public class Lock{

    // ReentrantLock
    private ReentrantLock lock=new ReentrantLock();

    // 对象构造
    static Lock account=new Lock(1300);
    private double money;
    public Lock(double money) {
        this.money = money;
    }

    // 取钱
    public void drawMoney(double count){
        lock.lock();
        try{
            if(money>=count){
                System.out.println(Thread.currentThread().getName()+"取钱成功，吐出钞票:"+count);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                money-=count;
                System.out.println("账户余额为"+money);
            }else{
                System.out.println(Thread.currentThread().getName()+"取钱失败，钞票不足！");
            }
        }finally{
            lock.unlock();
        }
    }

    // 模板方法实操
    public static class LockThread implements Runnable{
        @Override
        public void run() {
            account.drawMoney(600);
        }
    }

    // 主程序main
    public static void main(String[] args) {
        LockThread dt=new LockThread();
        new Thread(dt).start();
        new Thread(dt).start();
        new Thread(dt).start();
        new Thread(dt).start();
    }


}
