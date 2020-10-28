package com.data.concurrent.c_keyword;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * @Author: tianyong
 * @Date: 2020/10/27 17:32
 * @Description: 读写锁
 */
public class ReentrantReadWriteLocks {

    public static void main(String[] args){
        for(int i = 0; i < 10; i++){
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Cache.put("key", new String(Thread.currentThread().getName() + " joke"));
                }
            }, "threadW-"+ i).start();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    System.out.println(Cache.get("key"));
                }
            }, "threadR-"+ i).start();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Cache.clear();
                }
            }, "threadC-"+ i).start();
        }

    }
}

class Cache {
    static Map<String, Object> map = new HashMap<String, Object>();
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();
    // 获取一个key对应的value
    public static final Object get(String key) {
        r.lock();
        try {
            System.out.println("get " + Thread.currentThread().getName());
            return map.get(key);
        } finally {
            r.unlock();
        }
    }
    // 设置key对应的value，并返回旧有的value
    public static final Object put(String key, Object value) {
        w.lock();
        try {
            System.out.println("put " + Thread.currentThread().getName());
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }
    // 清空所有的内容
    public static final void clear() {
        w.lock();
        try {
            System.out.println("clear " + Thread.currentThread().getName());
            map.clear();
        } finally {
            w.unlock();
        }
    }

}
