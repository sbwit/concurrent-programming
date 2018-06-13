package chapter5.singleton;

/**
 * @Description 内部类初始化测试
 * @Author tanzhengqiang
 * @Date 2018/6/12 17:19
 **/
public class InnerClassTest {
    public InnerClassTest(){
        System.out.println("init InnerClassTest ...");
    }

    static  class InnerClass {
        public InnerClass(){
            System.out.println("init InnerClass ...");
        }
    }

    public static void main(String[] args) {

    }
}
