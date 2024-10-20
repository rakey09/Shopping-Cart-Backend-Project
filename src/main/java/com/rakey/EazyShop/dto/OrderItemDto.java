package com.rakey.EazyShop.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderItemDto {

    private Long ProductId;
    private String productName;
    private int quantity;
    private BigDecimal price;
}
