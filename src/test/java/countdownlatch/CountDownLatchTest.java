package countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @description CountDownLatch 测试
 *  CountDownLatch 允许一个或多个线程等待其他线程完成操作
 *
 * @Author beifang
 * @Create 2021/11/21 16:44
 */
public class CountDownLatchTest {

    static CountDownLatch c = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
                c.countDown();  // 调用 countDown() 方法时，计数器会减1，
                System.out.println(2);
                c.countDown();
            }
        }).start();
        c.await();  // 等待 计数器c 减为 0，才往下执行
        System.out.println("3");
    }
}
