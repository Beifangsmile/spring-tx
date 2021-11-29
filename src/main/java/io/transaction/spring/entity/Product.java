package io.transaction.spring.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Product {
    private Long id;
    private String productName;
    private BigDecimal productPrice;
    private Integer stockCount;
}