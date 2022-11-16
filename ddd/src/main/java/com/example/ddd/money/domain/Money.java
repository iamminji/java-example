package com.example.ddd.money.domain;

public class Money {

    // immutable class

    private final int value;

    public Money(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Money 를 새로 만든다.
     *
     * @param money Money
     * @return Money
     */
    public Money add(Money money) {
        return new Money(this.value + money.value);
    }

    public Money multiply(int multiplier) {
        return new Money(this.value * multiplier);
    }
}
