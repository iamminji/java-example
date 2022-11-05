package org.example.hexagonal.domain.policy;

import org.example.hexagonal.domain.entity.Event;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public interface EventParser {
    DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss.SSS").withZone(ZoneId.of("UTC"));

    Event parseEvent(String event);
}
