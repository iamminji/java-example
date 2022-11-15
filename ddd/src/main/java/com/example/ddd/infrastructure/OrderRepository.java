package com.example.ddd.infrastructure;

public interface OrderRepository<T> {

    T findOrderById(String t);

}
