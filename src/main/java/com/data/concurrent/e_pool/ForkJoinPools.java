package com.data.concurrent.e_pool;

import java.util.Random;
import java.util.concurrent.*;

/*
 * @Author: tianyong
 * @Date: 2020/10/26 17:37
 * @Description: ForkJoinPools：支持将一个任务拆分成多个小任务并行计算
 */
public class ForkJoinPools {


    // 没有返回值的并行计算
    public static class NoForkJoinPool extends RecursiveAction {
        private static final int smallTask=50;
        private int start;
        private int end;
        public NoForkJoinPool(int start,int end){
            this.start=start;
            this.end=end;
        }
        @Override
        protected void compute() {
            if(end-start<smallTask){//任务足够小，可以运行
                for(int i=start;i<end;i++){
                    System.out.println(Thread.currentThread().getName()+"的i的值"+i);
                }
            }else{
                //当任务不够小的时候，分解任务
                int middle=(start+end)/2;
                NoForkJoinPool left=new NoForkJoinPool(start, middle);
                NoForkJoinPool right=new NoForkJoinPool(middle, end);
                left.fork();
                right.fork();
            }
        }
        public static void main(String[] args) throws InterruptedException {
            ForkJoinPool pool=new ForkJoinPool();//cpu核数
            pool.submit(new NoForkJoinPool(0, 300));//提交要分解的任务
            pool.awaitTermination(2, TimeUnit.SECONDS);
            pool.shutdown();
        }
    }


    // 有返回值的并行计算
    public static class HasForkJoinPool extends RecursiveTask<Integer> {
        private static final int smallTask=20;
        private int arr[];
        private int start;
        private int end;
        public HasForkJoinPool(int[] arr, int start, int end) {
            super();
            this.arr = arr;
            this.start = start;
            this.end = end;
        }
        @Override
        protected Integer compute() {
            int sum=0;
            if(end-start<smallTask){
                for(int i=start;i<end;i++){
                    sum+=arr[i];
                }
                return sum;
            }
            else{
                int middle =(start+end)/2;
                HasForkJoinPool left=new HasForkJoinPool(arr, start, middle);
                HasForkJoinPool right=new HasForkJoinPool(arr, middle, end);
                left.fork();
                right.fork();
                return left.join()+right.join();
            }
        }
        public static void main(String[] args) throws InterruptedException, ExecutionException {
            int []arr=new int[100000000];
            Random random=new Random();
            int total=0;
            for(int i=0,len=arr.length;i<len;i++){
                int temp=random.nextInt(20);
                total+=(arr[i]=temp);
            }
            System.out.println("普通计算总量："+total);
            ForkJoinPool pool=ForkJoinPool.commonPool();
            Future<Integer>future=pool.submit(new HasForkJoinPool(arr, 0, arr.length));
            System.out.println("任务分解返回值"+future.get());
            pool.shutdown();
        }
    }


}
