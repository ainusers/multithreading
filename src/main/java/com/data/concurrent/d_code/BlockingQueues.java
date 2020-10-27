package com.data.concurrent.d_code;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
 * @Author: tianyong
 * @Date: 2020/10/26 11:40
 * @Description: BlockingQueues (漏桶算法思想)
 * @Description:  add()，offer()，put(),remove()，poll()，take(),element()，peek()
 * @Description:  此方法主要为了表达阻塞队列的思想，但是实际运行还存在问题
 *      (例：Thread-1生产者生产完成[java, java]
 *          一个生产者完成生产，实际阻塞队列中存在两个元素
 *      )
 */
public class BlockingQueues {

    // 服务提供者
    public static class Producer extends Thread{

        private BlockingQueue<String> bq;
        public Producer(BlockingQueue<String> bq){
            this.bq=bq;
        }

        @Override
        public void run() {
            String[] strArr=new String[]{"java","php","go","python","shell"};
            for(int i=0;i<5;i++){
                System.out.println(getName()+"生产者准备生产集合元素");
                try {
                    Thread.sleep(200);
                    //尝试put元素，如果队列已满，则阻塞
                    bq.put(strArr[i]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getName()+"生产者生产完成"+bq);
            }
        }
    }


    // 服务消费者
    public static class Consumer extends Thread{

        private BlockingQueue<String> bq;
        public Consumer(BlockingQueue<String> bq){
            this.bq=bq;
        }

        @Override
        public void run() {
            for(int i=0;i<5;i++){
                System.out.println(getName()+"消费者准备消费集合元素");
                try {
                    Thread.sleep(200);
                    //尝试put元素，如果队列已满，则阻塞
                    bq.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getName()+"消费者消费完成"+bq);
            }
        }
    }


    // 主函数测试入口
    public static void main(String[] args) {
        // 数字代表
        BlockingQueue<String> bq = new ArrayBlockingQueue<String>(2);
        // 生产者
        new Producer(bq).start();
        new Producer(bq).start();

        // 消费者
        new Consumer(bq).start();
    }


}
