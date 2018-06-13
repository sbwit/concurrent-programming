package chapter5.singleton.innerclass;

/**
 * @Description
 * @Author tanzhengqiang
 * @Date 2018/6/12 19:47
 **/
public abstract class Person {
    private String name;
    public abstract int walk();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
