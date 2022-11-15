package com.example.ddd.domain.shipping;

public class Address {

    private final String address1;
    private final String address2;
    private final String zipcode;

    public Address(String address1, String address2, String zipcode) {
        this.address1 = address1;
        this.address2 = address2;
        this.zipcode = zipcode;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getZipcode() {
        return zipcode;
    }
}
