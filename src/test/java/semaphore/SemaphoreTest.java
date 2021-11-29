package semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @description Semaphore 信号量， 用来控制同时访问特定资源的线程数量，它通过协调各个线程，以保证合理的使用公共资源
 *  应用场景：   可以用于做流量控制，特别是公共资源有限的应用场景，比如数据库链接
 * @Author beifang
 * @Create 2021/11/21 20:09
 */
public class SemaphoreTest {
    private static final int THREAD_COUNT = 30;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);

    private static Semaphore s = new Semaphore(10);

    public static void main(String[] args) {
        for(int i = 0; i<THREAD_COUNT;i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        s.acquire(); // 执行run() 方法必须先获取 信号量许可证 如果获取不到
                        System.out.println("save data ");
                        Thread.sleep(1000);
                        System.out.println(s.availablePermits() + s.getQueueLength());
                        s.release();    // 归还 信号量许可证
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
    }
}
