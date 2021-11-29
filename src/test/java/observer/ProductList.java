package observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/26 00:14
 */
public class ProductList extends Observable {

    private List<String> productList = null;

    private static ProductList instance;

    private ProductList(){}

    public static ProductList getInstance() {
        if(instance == null) {
            instance = new ProductList();
            instance.productList = new ArrayList<>();
        }
        return instance;
    }

    public void addProductionListObserver(Observer observer) {
        this.addObserver(observer);
    }

    public void addProduct(String newProduct) {
        productList.add(newProduct);
        System.out.println("产品列表更新");
        // 设置状态改变
        this.setChanged();
        // 通知观察这（发布）
        this.notifyObservers(newProduct);
    }

}
