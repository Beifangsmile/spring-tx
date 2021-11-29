package valatile;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/23 20:52
 */
public class VolatileTest {

    public static int race = 1;

    public static void increase() {
        race++;
    }

    public static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for(int i = 0; i<THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 1; i <= 1000; i++) {
                        increase();
                    }
                    System.out.println(race);
                }
            });
            threads[i].start();
        }

        while(Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(race);
    }

}
