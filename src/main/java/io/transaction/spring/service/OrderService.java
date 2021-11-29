package io.transaction.spring.service;

import io.transaction.spring.entity.Order;
import io.transaction.spring.repository.OrderDao;
import io.transaction.spring.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductService productService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void submitOrder() {
        Order order = new Order();
        long number = Math.abs(new Random().nextInt(500));
        order.setId(number);
        order.setOrderNo("order_" + number);
        orderDao.saveOrder(order);
        productService.updateProductStockCountById(1,1L);
    }
}
