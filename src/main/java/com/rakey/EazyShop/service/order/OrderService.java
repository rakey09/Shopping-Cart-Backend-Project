package com.rakey.EazyShop.service.order;

import com.rakey.EazyShop.dto.OrderDto;
import com.rakey.EazyShop.enums.OrderStatus;
import com.rakey.EazyShop.exceptions.ResourceNotFoundException;
import com.rakey.EazyShop.model.Cart;
import com.rakey.EazyShop.model.Order;
import com.rakey.EazyShop.model.OrderItem;
import com.rakey.EazyShop.model.Product;
import com.rakey.EazyShop.repository.OrderRepository;
import com.rakey.EazyShop.repository.ProductRepository;
import com.rakey.EazyShop.service.cart.ICartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderService implements IOrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        Order savedOder = orderRepository.save(order);

        cartService.clearCart(cart.getId());
        return savedOder;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems){
        return orderItems
                .stream()
                .map(item->item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity()))).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::covertToDto)
                .orElseThrow(()->new ResourceNotFoundException("Order Not Fount"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId){
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::covertToDto).toList();
    }

    private OrderDto covertToDto(Order order){
        return modelMapper.map(order, OrderDto.class);
    }
}
