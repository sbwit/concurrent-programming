package chapter5.singleton.innerclass;

/**
 * @Description 内部类示例
 * 参考文章：https://www.jianshu.com/p/8b8bfe1fd488
 *
 * @Author tanzhengqiang
 * @Date 2018/6/12 19:46
 **/
public class OuterClass {

    private static String outerStaticStr;
    private int outerInt;

    // 普通方法
    public void outerDisplay(){
        System.out.println("OuterClass outerDisplay Method");
    }

    // 静态方法
    public static void outerStaticDisplay(){
        System.out.println("OuterClass outerStaticDisplay Method");
    }

    /*成员内部类*/
    public class InnerClass{
        public String innerStr; //成员内部类不能声明静态变量
        static final int innerInt = 100; // 静态常量
        public void innerDisplay(){
            outerStaticStr = "Tom";  // 使用外部类的成员变量
            System.out.println("成员内部类 " + outerStaticStr);
            outerDisplay();// 使用外部类的方法
            outerStaticDisplay();
        }
    }

    /*静态内部类*/
    public static class StaticInnerClass {
        private String innerStr;
        public static String innerStaticStr; // 与成员内部类不同，静态内部类可以声明静态变量
        public void innerDisplay() {
            //静态内部类可以直接访问外部类的静态成员数据。
            outerStaticStr = "Jerry";
            System.out.println("静态内部类 " + outerStaticStr);
            outerStaticDisplay();
        }
    }

    /*局部内部类*/
    public void outPut(String string) {
        int a = 20;
        outerInt = 999;
        class LocalInnerClass { // 此时局部内部类与局部变量同一等级，局部不能加private等权限访问修饰词修饰。
            static final int b = 20; //静态常量
            public void localOutPut() {
                //string = "局部内部类"; //不能再赋值
                //a = 30; //不能再赋值

                System.out.println(string);
                System.out.println(outerInt);
            }
        }
        // 只能在方法内实例化
        LocalInnerClass localInner = new LocalInnerClass();
        localInner.localOutPut();
    }

    /*匿名内部类*/
    public void test(Person per){
        System.out.println(per.getName() + "今天走了" + per.walk() + "米。");
    }


    public InnerClass getInnerClass(){
        return new InnerClass();
    }
    public StaticInnerClass getStaticInnerClass(){
        return new StaticInnerClass();
    }

}
