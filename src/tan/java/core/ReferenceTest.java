package tan.java.core;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @Description 强 弱 软 虚 引用示例
 *
 * @Author tanzhengqiang
 * @Date 2018/6/13 13:08
 **/
public class ReferenceTest {
    public static void main(String[] args) {
        // name -> 0x1000
        // softReference ->0x1000
        // 软引用 - 对于堆上的内存地址0x1000 ,分别有强弱引用来引用它，现在我们将这块地址上的强引用干掉后发现，即使手动触发gc，也不会
        //回收这块内存空间，因为在同一块内存空间上若是只有软引用时候，内存空间足够就不进行垃圾回收动作。
        String name = new String("zhengqiang");
        SoftReference<String> softReference = new SoftReference<>(name);
        name = null;
        System.gc();
        System.out.println(softReference.get());// not null
        System.out.println();

        // 弱引用 -  垃圾回收器一旦发现某块内存上只有弱引用，不管当前内存空间是否足够，则一定会强制回收。
        String str = new String("aaa");
        WeakReference<String> weakRef = new WeakReference<>(str);
        str = null; // 去掉强引用
        System.gc();
        System.out.println(weakRef.get());// 输出为null




    }
}
