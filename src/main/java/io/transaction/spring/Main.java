package io.transaction.spring;

import io.transaction.spring.config.MainConfig;
import io.transaction.spring.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        OrderService orderService = context.getBean(OrderService.class);
        orderService.submitOrder();

    }
}