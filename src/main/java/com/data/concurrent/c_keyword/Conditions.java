package com.data.concurrent.c_keyword;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * @Author: tianyong
 * @Date: 2020/10/26 10:52
 * @Description: Condition (await()，signal()，signalAll())
 */
public class Conditions {

    private double balance;
    private boolean flag=false;//已有存款标志
    private final Lock lock=new ReentrantLock();
    private final Condition condition=lock.newCondition();

    public Conditions(double balance) {
        super();
        this.balance = balance;
    }

    // 取钱
    public void draw(double count){
        lock.lock();
        try{
            if(!flag){  //为假，所以没有人存钱进去，取钱阻塞
                condition.await();
            }else{//可以取钱
                if(balance>=count){
                    System.out.print(Thread.currentThread().getName()+"取钱:"+count);
                    balance-=count;
                    System.out.println(" 取钱成功,账户余额:"+balance);
                    flag=false;
                    condition.signalAll();  //唤醒存钱线程
                }else{//余额不足
                    System.out.println("想取"+count+"账户余额不足:"+balance);
                    flag=false;
                    condition.signalAll();  //唤醒存钱线程
                }
            }
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    // 存钱
    public  void deposit(double count){
        lock.lock();
        try{
            if(flag){
                condition.await();  //没人取钱，则存钱阻塞
            }else{
                System.out.print(Thread.currentThread().getName()+"存钱:"+count);
                balance+=count;
                System.out.println(" 存钱成功,账户余额:"+balance);
                flag=true;
                condition.signalAll();  //唤醒取钱线程
            }
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    // 取钱 (消费者)
    public static class DrawThread extends Thread{
        private Conditions c;
        private double count;  //取钱数
        public DrawThread(String name,Conditions c,double count){
            super(name);
            this.c=c;
            this.count=count;
        }
        public void run() {
            for(int i=0;i<10;i++){
                c.draw(count);
            }
        }
    }


    // 存钱 (生产者)
    public static class DepositThread extends Thread{
        private Conditions c;
        private double depositAmount;  //取钱数
        public DepositThread(String name,Conditions c,double depositAmount){
            super(name);
            this.c=c;
            this.depositAmount=depositAmount;
        }
        public void run() {
            for(int i=0;i<10;i++){
                c.deposit(depositAmount);
            }
        }
    }


    // main主函数
    public static void main(String[] args) {
        Conditions conditions=new Conditions( 0);
        new DrawThread("取款者", conditions, 1200).start();

        new DepositThread("甲", conditions, 1000).start();
        new DepositThread("乙", conditions, 1000).start();
        new DepositThread("丙", conditions, 1000).start();
    }



}
