package org.example.hexagonal.domain.vo;

public class EventId {

    private String id;

    public EventId(String id) {
        this.id = id;
    }

    public static EventId of(String id) {
        return new EventId(id);
    }
}
