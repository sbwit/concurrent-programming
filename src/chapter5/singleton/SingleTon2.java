package chapter5.singleton;

/**
 * @Description 懒加载模式的单例，延迟初始化
 *
 * 优点：支持延迟加载，只会在instance第一次创建的时候初始化
 * 缺点：并发环境下加锁在竞争激烈的场合性能可能产生一定的影响
 *
 * @Author tanzhengqiang
 * @Date 2018/6/12 17:03
 **/
public class SingleTon2 {

    private SingleTon2(){
        System.out.println("init SingleTon2 !");
    }
    private static SingleTon2 instance = null;
    public static synchronized  SingleTon2 getInstance() {
        if (instance == null) {
            instance = new SingleTon2();
        }
        return  instance;
    }

    public static void main(String[] args) {
        System.out.println(SingleTon2.getInstance());
        System.out.println(SingleTon2.getInstance());
        System.out.println(SingleTon2.getInstance());
    }

}
