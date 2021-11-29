import org.junit.Test;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author beifang
 * @Create 2021/11/19 15:43
 */
public class FairAndUnfairTest {
    private static ReentrantLock2 unfairLock = new ReentrantLock2(false);
    private static ReentrantLock2 fairLock = new ReentrantLock2(true);

    @Test
    public void fair() {
        testLock(fairLock);

    }

    @Test
    public void unfair() {
        testLock(unfairLock);
    }

    private void testLock(ReentrantLock2 lock) {

        for(int i = 0;i<5;i++) {
            Job job = new Job(lock);
            job.setDaemon(true);
            job.setName("线程" + i);
            job.start();
            //System.out.println(i);
        }
    }

    private static class Job extends Thread {
        private ReentrantLock2 lock;
        public Job(ReentrantLock2 lock) {
            this.lock = lock;
        }
        public void run() {
                //Thread.sleep(1000);
            while(true) {
                lock.lock();
                try{
                    Collection<Thread> list = lock.getQueuedThreads();
                    System.out.print("Lock by [" + Thread.currentThread().getName() + "], Waitting by [");
                    for(Thread t : list){
                        System.out.print(t.getName() + ",");
                    }
                    System.out.println();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    System.out.println("----------");
                    lock.unlock();
                }
            }




            }
        }

    private static class ReentrantLock2 extends ReentrantLock {
        /**
         * 构造方法，指定是否为公平锁
         * @param fair
         */
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads () {
            List<Thread> arrayList = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
}
