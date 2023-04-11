package org.example.log;

import java.time.LocalDateTime;

public class LogEntry {
    private LogLevel level;
    private String message;
    private LocalDateTime timestamp;

    public LogLevel getLevel() {
        return level;
    }

    public LogEntry setLevel(LogLevel level) {
        this.level = level;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LogEntry setMessage(String message) {
        this.message = message;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LogEntry setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
            "level=" + level +
            ", message='" + message + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}