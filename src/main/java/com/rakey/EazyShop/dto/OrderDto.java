package com.rakey.EazyShop.dto;

import com.rakey.EazyShop.enums.OrderStatus;
import com.rakey.EazyShop.model.OrderItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Data
public class OrderDto {

    private Long OrderId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;

    private Set<OrderItemDto> items ;
}
