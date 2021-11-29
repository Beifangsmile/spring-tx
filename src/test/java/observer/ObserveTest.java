package observer;

import org.junit.Test;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/26 00:19
 */
public class ObserveTest {

    /**
     * 观察这模式
     */
    @Test
    public void test() {
        ProductList observer = ProductList.getInstance();
        TaobaoObserver taobaoObserver = new TaobaoObserver();
        JingDongObserver jingDongObserver = new JingDongObserver();
        observer.addObserver(jingDongObserver);
        observer.addObserver(taobaoObserver);
        observer.addProduct("新增产品1");
    }
}
