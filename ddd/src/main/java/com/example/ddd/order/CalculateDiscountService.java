package com.example.ddd.order;

import com.example.ddd.customer.domain.Customer;
import com.example.ddd.money.domain.Money;
import com.example.ddd.order.domain.OrderLine;
import com.example.ddd.order.domain.RuleDiscounter;
import java.util.List;

public class CalculateDiscountService {

    private RuleDiscounter ruleDiscounter;

    public CalculateDiscountService(RuleDiscounter ruleDiscounter) {
        this.ruleDiscounter = ruleDiscounter;
    }

    public Money calculateDiscount(List<OrderLine> orderLineList, String customerId) {
        Customer customer = findCustomer(customerId);
        return ruleDiscounter.applyRules(customer, orderLineList);
    }

    // temp
    private Customer findCustomer(String id) {
        return null;
    }
}
