package com.example.ddd.order.domain.service;

import com.example.ddd.order.domain.Order;
import com.example.ddd.order.domain.OrderRepository;
import com.example.ddd.order.exception.OrderNotFoundException;

public class CancelOrderService {

    private OrderRepository<Order> orderRepository;

    public void cancelOrder(String orderId) {
        Order order = orderRepository.findOrderById(orderId);
        if (order == null) throw new OrderNotFoundException(orderId);
        order.cancel();
    }

}
