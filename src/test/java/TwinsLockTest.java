import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * @Author beifang
 * @Create 2021/11/18 22:55
 */
public class TwinsLockTest {
    @Test
    public void Test() {

        final Lock lock = new TwinLock();

        class Worker extends Thread {
            public void run() {
                while(true) {
                    lock.lock();
                    try{
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }
            }
        }
        for(int i = 0;i<10;i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        for(int i = 0;i<10;i++) {
            try {
                Thread.sleep(1000);
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
