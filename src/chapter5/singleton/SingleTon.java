package chapter5.singleton;

/**
 * 饿汉式单例-性能非常好，无锁操作
 * 缺点：实例的创建不受控制，对于instance它会在第一次初始化的时候创建
 */
public class SingleTon {

    private static int status=1;
    private SingleTon(){
        System.out.println("singleton was created!");
    }

    private static  SingleTon instance = new SingleTon();

    public static SingleTon getInstance(){

        return instance;
    }

    public static void main(String[] args) {
        System.out.println(SingleTon.status + " ---- " + SingleTon.instance);
        System.out.println(SingleTon.status + " ---- " + SingleTon.instance);
        System.out.println(SingleTon.status + " ---- " + SingleTon.instance);

    }

}
//    singleton was created!
//        1 ---- chapter5.singleton.SingleTon@60e53b93
//        1 ---- chapter5.singleton.SingleTon@60e53b93
//        1 ---- chapter5.singleton.SingleTon@60e53b93