package com.data.concurrent.pattern;

/*
 * @Author: tianyong
 * @Date: 2020/10/27 14:20
 * @Description: 单例设计模式 （对象创建方式）
 */
public class Single {

    // 私有构造
    private Single() {}

    // 创建driver承载变量
    private static final ThreadLocal<Single> tl = new ThreadLocal<>();

    /*
     * @Author: tianyong
     * @Date: 2020/9/8 14:04
     * @Description: 创建driver对象
     */
    public static Single getInstance(){
        // 提前判空，效率较高，单例设计模式中，唯有第一个进入的人可以获取对象
        // 其他人进来之后，先提前判空，就不用进入同步代码块中，提高效率
        if(tl.get() == null){
            synchronized (Single.class){
                if(tl.get() == null){
                    tl.set(new Single());
                }
            }
        }
        return tl.get();
    }

}
