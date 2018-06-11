package chapter3;

import java.util.concurrent.*;

/**
 * 自定义拒绝策略
 */
public class RejectThreadPoolDemo {
    public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()
                    + ":Thread ID:" + Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws InterruptedException {

        // 一般用法
//        int corePoolSize = 1;
//        int maximumPoolSize = 1;
//        BlockingQueue queue = new  ArrayBlockingQueue<Runnable>(1);
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize,  maximumPoolSize,
//                0, TimeUnit.SECONDS, queue ) ;
//        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy ());
//        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
//        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
//        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());



        MyTask myTask = new MyTask();

        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(10), Executors.defaultThreadFactory()
                , new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //打印丢弃的任务
                System.out.println(r.toString() + " is discard");
            }
        });

        for (int i = 0; i < 100; i++) {
            executorService.submit(myTask);
            Thread.sleep(10);
        }
    }
}
