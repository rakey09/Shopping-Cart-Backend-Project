package com.rakey.EazyShop.service.order;

import com.rakey.EazyShop.dto.OrderDto;
import com.rakey.EazyShop.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
