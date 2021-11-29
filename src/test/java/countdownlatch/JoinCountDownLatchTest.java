package countdownlatch;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/23 21:12
 */
public class JoinCountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        Thread parser1 = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        Thread parser2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parser2 finish");
            }
        });

        parser1.start();
        parser2.start();
//        parser1.join();       // 让当前线程（主线程）等待 parser1 线程结束
//        parser2.join();
        System.out.println("all parser finish");
    }
}
