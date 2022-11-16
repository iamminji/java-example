package com.example.ddd.shipping.domain;

public class ShippingInfo {
    // Value 타입은 개념적으로 완전한 하나를 표현할 때 사용한다.

    private Receiver receiver;
    private Address address;

    public ShippingInfo(Receiver receiver, Address address) {
        this.receiver = receiver;
        this.address = address;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public Address getAddress() {
        return address;
    }
}
