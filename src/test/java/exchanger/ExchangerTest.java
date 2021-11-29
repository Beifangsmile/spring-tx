package exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/21 20:17
 */
public class ExchangerTest {
    private static final Exchanger<String> exgr = new Exchanger<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String A = "银行流水A";
                    String temp = exgr.exchange(A);
                    System.out.println(Thread.currentThread().getName() + ":" +temp);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                String B = "银行流水B";
                String A = exgr.exchange(B);
                System.out.println("A和B数据是否一致：" + A.equals(B) + ",A录入的数据是： " + A + "，" +
                        "B录入的数据是：" + B );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPool.shutdown();
    }
}
