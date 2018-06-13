package chapter5.singleton;

/**
 * @Description 使用内部类创建单例的方式
 *
 * 优点：支持延迟加载，无锁化性能更高
 *
 * 原理：巧妙使用了内部类和类的初始化机制
 *
 * @Author tanzhengqiang
 * @Date 2018/6/12 17:03
 **/
public class SingleTon3 {

    private SingleTon3(){
        System.out.println("init SingleTon3 !");

    }
    private static class Holder {
        private static SingleTon3 instance = new SingleTon3();
    }

    public static SingleTon3 getInstance() {
        return Holder.instance;
    }

    public static void main(String[] args) {
        System.out.println(SingleTon3.getInstance());
        System.out.println(SingleTon3.getInstance());
        System.out.println(SingleTon3.getInstance());
    }

}
