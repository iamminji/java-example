package org.example.hexagonal.domain.entity;

import org.example.hexagonal.domain.policy.RegexEventParser;
import org.example.hexagonal.domain.policy.SplitEventParser;
import org.example.hexagonal.domain.vo.Activity;
import org.example.hexagonal.domain.vo.EventId;
import org.example.hexagonal.domain.vo.ParsePolicyType;
import org.example.hexagonal.domain.vo.Protocol;

import java.time.OffsetDateTime;

public class Event implements Comparable<Event> {

    private OffsetDateTime timestamp;
    private EventId id;
    private Protocol protocol;
    private Activity activity;

    public Event(OffsetDateTime timestamp, EventId id, Protocol protocol, Activity activity) {
        this.timestamp = timestamp;
        this.id = id;
        this.protocol = protocol;
        this.activity = activity;
    }

    public static Event parsedEvent(String unparsedEvent, ParsePolicyType policy) {
        switch (policy) {
            case REGEX -> {
                return new RegexEventParser().parseEvent(unparsedEvent);
            }
            case SPLIT -> {
                return new SplitEventParser().parseEvent(unparsedEvent);
            }
            default -> throw new IllegalArgumentException("Invalid policy");
        }
    }

    @Override
    public int compareTo(Event o) {
        return 0;
    }
}
