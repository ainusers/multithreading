package com.data.concurrent.c_keyword;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * @Author: tianyong
 * @Date: 2020/10/27 17:32
 * @Description: 读写锁
 * @Description: 1. 支持公平锁，非公平锁 (默认)
 * @Description: 2. 支持重入
 * @Description: 3. 锁降级 (锁降级指的是写锁降级成为读锁。如果当前线程拥有写锁，然后将其释放，最后再获取读锁，这种分段完成的过程不能称之为锁降级。锁降级是指把持住（当前拥有的）写锁，再获取到读锁，随后释放（先前拥有的）写锁的过程)
 * @Description: 4. 也就是先获取写锁、获取读锁在释放写锁的顺序 (锁降级主要是为了：减少线程的阻塞和唤醒、数据的可见性 [保证数据不会造成脏读])
 */
public class ReentrantReadWriteLocks {

    public static void main(String[] args){
        for(int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Cache.put("key", new String(Thread.currentThread().getName() + " - joke"));
                }
            }, "thread - "+ i).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Cache.get("key"));
                }
            }, "thread - "+ i).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Cache.clear();
                }
            }, "thread - "+ i).start();
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
            System.out.println(Thread.currentThread().getName() + " - get");
            return map.get(key);
        } finally {
            r.unlock();
        }
    }
    // 设置key对应的value，并返回旧有的value
    public static final Object put(String key, Object value) {
        w.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " - put");
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }
    // 清空所有的内容
    public static final void clear() {
        w.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " - clear");
            map.clear();
        } finally {
            w.unlock();
        }
    }

}
