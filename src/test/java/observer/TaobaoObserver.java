package observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/26 00:18
 */
public class TaobaoObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        String newProduct = (String) arg;
        System.out.println("发送新产品，同步到淘宝商城");
    }
}
