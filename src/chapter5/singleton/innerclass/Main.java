package chapter5.singleton.innerclass;

/**
 * @Description Test
 *
 * @Ref https://www.jianshu.com/p/8b8bfe1fd488
 * @Author tanzhengqiang
 * @Date 2018/6/12 19:49
 **/
public class Main {
    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        //OuterClass.InnerClass inner = outer.getInnerClass();
        OuterClass.InnerClass inner = new OuterClass().new InnerClass();// 非静态内部类的创建需要依赖于外部类。
        inner.innerDisplay();


        OuterClass.StaticInnerClass staticInner = outer.getStaticInnerClass();
        staticInner.innerDisplay();

        outer.outPut("局部内部类");

        /*匿名内部类*/
        outer.test(new Person() {

            public int walk() { // 实现抽象方法
                return 500;
            }

            public String getName() { // 重写方法
                return "小明";
            }
        });

        /*
         * test()方法接受一个Person类型的参数，同时我们知道一个抽象类是没有办法直接new的。
         * 我们必须要先有实现类才能new出来它的实现类实例。
         * 所以在方法中直接使用匿名内部类来创建一个Person实例。
         * 由于匿名内部类不能是抽象类，所以它必须要实现它的抽象父类或者接口里面所有的抽象方法。
         * */

    }
}
