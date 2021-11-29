package cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @description CyclicBarrier 测试
 *  CyclicBarrier 可循环使用的屏障，让一组线程到达一个屏障（同步点）时被阻塞，直到最后一个线程到达屏障，屏障才会开门
 *  所有被屏障拦截的线程才会继续运行
 * @Author beifang
 * @Create 2021/11/21 19:39
 */
public class CyclicBarrierTest {

    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();      // 表示当前线程到达了屏障
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(1);
            }
        }).start();

        try{
            c.await();  // 表示当前线程（主线程）到达了屏障
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(2);
    }
}
