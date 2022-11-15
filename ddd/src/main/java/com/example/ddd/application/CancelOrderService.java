package com.example.ddd.application;

import com.example.ddd.domain.order.Order;
import com.example.ddd.domain.order.exception.OrderNotFoundException;
import com.example.ddd.infrastructure.OrderRepository;

public class CancelOrderService {

    private OrderRepository<Order> orderRepository;

    public void cancelOrder(String orderId) {
        Order order = orderRepository.findOrderById(orderId);
        if (order == null) throw new OrderNotFoundException(orderId);
        order.cancel();
    }

}
