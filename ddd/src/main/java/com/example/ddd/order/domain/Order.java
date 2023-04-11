package com.example.ddd.order.domain;

import com.example.ddd.money.domain.Money;

import java.util.List;

// Order Entity
// Aggregate Root Domain Entity
public class Order {

    private OrderNo orderNumber;

    private OrderLines orderLines;
    private Money totalAmounts;
    private ShippingInfo shippingInfo;
    private OrderState orderState;

    public Order(List<OrderLines> orderLines, ShippingInfo shippingInfo) {
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        if (shippingInfo == null) {
            throw new IllegalArgumentException("no ShippingInfo");
        }
        // Value가 불변이면 새로운 객체를 할당해서 값을 변경해야 한다.
        this.shippingInfo = shippingInfo;
    }

    private void setOrderLines(OrderLines orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();

    }

    private void verifyAtLeastOneOrMoreOrderLines(OrderLines orderLines) {
        if (orderLines == null) {
            throw new IllegalArgumentException("no OrderLines");
        }
    }

    private void calculateTotalAmounts() {
        int sum = orderLines.stream()
            .mapToInt(o -> o.getAmounts().getValue())
            .sum();
        this.totalAmounts = new Money(sum);
    }

    /**
     * 출고 상태로 변경하기
     */
    public void changeShipped() {

    }

    public void changeOrderLines(List<OrderLines> newLines) {
    }

    /**
     * 배송지 정보 변경하기
     *
     * @param newShipping
     */
    public void changeShippingInfo(ShippingInfo newShipping) {
        // 애그리거트 루트는 도메인 규칙을 구현한 기능을 제공한다.
        verifyNotYetShipped();
        setShippingInfo(newShipping);
    }

    /**
     * 주문 취소하기
     */
    public void cancel() {
        verifyNotYetShipped();
        this.orderState = OrderState.CANCELED;
    }

    private void verifyNotYetShipped() {
        if (orderState != OrderState.PAYMENT_WAITING &&
            orderState != OrderState.PREPARING) {
            throw new IllegalStateException("already shipped!");
        }
    }

    /**
     * 결제 완료하기
     */
    public void completePayment() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderNumber == null) ? 0 : orderNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != Order.class) {
            return false;
        }
        Order other = (Order) obj;
        if (this.orderNumber == null) {
            return false;
        }
        return this.orderNumber.equals(other.orderNumber);
    }
}
