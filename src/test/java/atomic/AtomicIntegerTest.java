package atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/23 21:04
 */
public class AtomicIntegerTest {
    static AtomicInteger ai = new AtomicInteger(1);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement()); // 先获取在 +1
        System.out.println(ai.get());
    }

}
