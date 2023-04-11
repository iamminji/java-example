package com.example.ddd.order;

import com.example.ddd.customer.domain.Customer;
import com.example.ddd.money.domain.Money;
import com.example.ddd.order.domain.OrderLines;
import com.example.ddd.order.domain.RuleDiscounter;
import java.util.List;

public class CalculateDiscountService {

    private RuleDiscounter ruleDiscounter;

    public CalculateDiscountService(RuleDiscounter ruleDiscounter) {
        this.ruleDiscounter = ruleDiscounter;
    }

    public Money calculateDiscount(List<OrderLines> orderLinesList, String customerId) {
        Customer customer = findCustomer(customerId);
        return ruleDiscounter.applyRules(customer, orderLinesList);
    }

    // temp
    private Customer findCustomer(String id) {
        return null;
    }
}
