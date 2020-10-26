package com.data.concurrent.demo;

/*
 * @Author: tianyong
 * @Date: 2020/10/23 18:05
 * @Description: Synchronized (wait(),notifyAll() 线程通信)
 */
public class Synchronized {

    private double balance;
    private boolean flag=false;//已有存款标志
    public Synchronized(double balance) {
        this.balance = balance;
    }

    // 取钱
    public synchronized void draw(double count){
        try{
            if(!flag){//为假，所以没有人存钱进去，取钱阻塞
                wait();
            }else{//可以取钱
                if(balance>=count){
                    System.out.print(Thread.currentThread().getName()+"取钱:"+count);
                    balance-=count;
                    System.out.println(" 取钱成功,账户余额:"+balance);
                    flag=false;
                    notifyAll();//唤醒存钱线程
                }else{//余额不足
                    System.out.println("想取"+count+"账户余额不足:"+balance);
                    flag=false;
                    notifyAll();//唤醒存钱线程
                }
            }
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }
    // 存钱
    public synchronized void deposit(double depositAmount){
        try{
            if(flag){
                wait();//没人取钱，则存钱阻塞
            }else{
                System.out.print(Thread.currentThread().getName()+"存钱:"+depositAmount);
                balance+=depositAmount;
                System.out.println(" 存钱成功,账户余额:"+balance);
                flag=true;
                notifyAll();//唤醒取钱线程
            }
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }

    // 取钱 (消费者)
    public static class DrawThread extends Thread{
        private Synchronized sync;
        private double count;//取钱数
        public DrawThread(String name,Synchronized sync,double count){
            super(name);
            this.sync=sync;
            this.count=count;
        }
        public void run() {
            for(int i=0;i<10;i++){
                sync.draw(count);
            }
        }
    }


    // 存钱 (生产者)
    public static class DepositThread extends Thread{
        private Synchronized sync;
        private double depositAmount;//取钱数
        public DepositThread(String name,Synchronized sync,double depositAmount){
            super(name);
            this.sync=sync;
            this.depositAmount=depositAmount;
        }
        public void run() {
            for(int i=0;i<10;i++){
                sync.deposit(depositAmount);
            }
        }
    }


    // main主函数
    public static void main(String[] args) {
        Synchronized sync=new Synchronized( 0);
        new DrawThread("取款者", sync, 1200).start();

        new DepositThread("甲", sync, 1000).start();
        new DepositThread("乙", sync, 1000).start();
        new DepositThread("丙", sync, 1000).start();
    }


}
