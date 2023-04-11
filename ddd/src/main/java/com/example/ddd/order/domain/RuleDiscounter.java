package com.example.ddd.order.domain;

import com.example.ddd.customer.domain.Customer;
import com.example.ddd.money.domain.Money;
import java.util.List;

public interface RuleDiscounter {
    Money applyRules(Customer customer, List<OrderLines> orderLinesList);
}
