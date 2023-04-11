package com.example.ddd.order.infrastructure;

import com.example.ddd.customer.domain.Customer;
import com.example.ddd.money.domain.Money;
import com.example.ddd.order.domain.OrderLines;
import com.example.ddd.order.domain.RuleDiscounter;
import java.util.List;

public class DroolsRuleDiscounter implements RuleDiscounter {

    @Override
    public Money applyRules(Customer customer, List<OrderLines> orderLinesList) {
        return null;
    }
}
