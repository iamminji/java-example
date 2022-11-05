package org.example.hexagonal.domain.service;

import org.example.hexagonal.domain.entity.Event;
import org.example.hexagonal.domain.vo.ParsePolicyType;

import java.util.ArrayList;
import java.util.List;

public class EventSearch {

    public List<Event> retrieveEvents(List<String> unparsedEvents, ParsePolicyType parsePolicyType) {
        var parsedEvents = new ArrayList<Event>();
        unparsedEvents.stream().forEach(event -> {
            parsedEvents.add(Event.parsedEvent(event, parsePolicyType));
        });
        return parsedEvents;
    }
}
