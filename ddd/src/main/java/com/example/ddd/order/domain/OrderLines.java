package com.example.ddd.order.domain;

import com.example.ddd.money.domain.Money;
import com.example.ddd.product.domain.Product;
import java.util.List;

public class OrderLines {

    private Product product;
    private Money price;
    private int quantity;
    private Money amounts;

    public OrderLines(Product product, Money price, int quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }

    private Money calculateAmounts() {
        return price.multiply(quantity);
    }

    public Money getAmounts() {
        return amounts;
    }

    public void changeOrderLines(OrderLines orderLines) {
        this.product = orderLines.product;
        this.price = orderLines.price;
        this.quantity = orderLines.quantity;
        this.amounts = orderLines.amounts;
    }
}
