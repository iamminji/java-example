package com.example.ddd.order.domain;

public interface OrderRepository<T> {

    T findOrderById(String t);

}
