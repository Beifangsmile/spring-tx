package cyclicbarrier;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @description CyclicBarrier 测试
 *
 *  使用场景：CyclicBarrier 可以用于多线程计算数据，最后合并计算记过的场景。
 *  CyclicBarrier字面意思是可循环使用的屏障。 它要做的事情是，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，知道最后一个线程
 *  到达屏障是，屏障才会开门，所有被屏蔽拦截的线程才会继续
 *
 *
 * @Author beifang
 * @Create 2021/11/21 19:47
 */
public class BankWaterService implements Runnable {
    /**
     * 创建4个屏障，处理完之后执行当前类的run方法
     */
    private CyclicBarrier c = new CyclicBarrier(4, this);

    /**
     * 假设只有4个sheet,所以只启动4个线程
     */
    private Executor executor = Executors.newFixedThreadPool(4);

    private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();

    private void count() {
        for( int i = 0; i<4; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    sheetBankWaterCount.put(Thread.currentThread().getName(),1);
                    try {
                        c.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println("结果输出之后");
                }
            });
        }
    }

    @Override
    public void run() {
        int result = 0;
        for(Map.Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
            result += sheet.getValue();
        }
        sheetBankWaterCount.put("result", result);
        System.out.println(result);
        System.out.println("结果数据之后，主线程完成");
    }

    public static void main(String[] args) {
        BankWaterService bankWaterCount = new BankWaterService();
        bankWaterCount.count();
    }
}

