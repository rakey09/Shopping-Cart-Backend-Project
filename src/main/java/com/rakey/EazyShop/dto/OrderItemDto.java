package com.rakey.EazyShop.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderItemDto {

    private Long ProductId;
    private String productName;
    private String productBrand;
    private int quantity;
    private BigDecimal price;
}
