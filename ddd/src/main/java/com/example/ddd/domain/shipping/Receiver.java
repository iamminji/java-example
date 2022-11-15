package com.example.ddd.domain.shipping;

import java.util.Objects;

public class Receiver {

    private final String name;
    private final String phoneNumber;

    public Receiver(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Receiver receiver)) {
            return false;
        }

        if (!Objects.equals(name, receiver.name)) {
            return false;
        }
        return Objects.equals(phoneNumber, receiver.phoneNumber);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }
}
