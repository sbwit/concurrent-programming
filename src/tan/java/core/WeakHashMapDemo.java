package tan.java.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @Description WeakHashMapDemo
 *
 * https://blog.csdn.net/GoGleTech/article/details/80346114
 *
 * @Author tanzhengqiang
 * @Date 2018/6/13 13:24
 **/
public class WeakHashMapDemo {
    public static void main(String[] args) {
        String a = new String("a");
        String b = new String("b");

        Map<String, String> map = new HashMap<>();
        map.put(a,"aaa");
        map.put(b,"bbb");

        Map<String, String> weakHashMap = new WeakHashMap<String,String>();
        weakHashMap.put(a,"aaa");
        weakHashMap.put(b,"bbb");

        map.remove(a);//移除键是a引用的键值对
        a = null;
        b = null;

        System.gc();

        /*
        * 虽然我们没有从weakHashMap重移除a这个引用，但是之前a所对应内存地址上的强引用已经被移除
        * 只有一个弱引用。33行a所在内存地址的内存已经被回收，故而weakHashMap看不到a,只能看到b
        * */

        Iterator<Map.Entry<String, String>> iter1 = map.entrySet().iterator();
        while (iter1.hasNext()) {
            Map.Entry<String, String> next = iter1.next();
            System.out.println(next.getKey() + " --- " + next.getValue());
        }

        System.out.println("-----------");

        Iterator<Map.Entry<String, String>> iter2 = weakHashMap.entrySet().iterator();
        while (iter2.hasNext()) {
            Map.Entry<String, String> next = iter2.next();
            System.out.println(next.getKey() + " --- " + next.getValue());
        }


    }

}

