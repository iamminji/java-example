package org.example.log;

import org.example.parser.LoggerBaseListener;
import org.example.parser.LoggerParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class LogListener extends LoggerBaseListener {

    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER
        = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss", Locale.ENGLISH);
    private List<LogEntry> entries = new ArrayList<>();
    private LogEntry current;

    @Override
    public void enterEntry(LoggerParser.EntryContext ctx) {
        this.current = new LogEntry();
    }

    public List<LogEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    @Override
    public void exitEntry(LoggerParser.EntryContext ctx) {
        this.entries.add(this.current);
    }

    @Override
    public void enterTimestamp(LoggerParser.TimestampContext ctx) {
        this.current.setTimestamp(LocalDateTime.parse(ctx.getText(), DEFAULT_DATETIME_FORMATTER));
    }

    @Override
    public void enterLevel(LoggerParser.LevelContext ctx) {
        this.current.setLevel(LogLevel.valueOf(ctx.getText()));
    }

    @Override
    public void enterMessage(LoggerParser.MessageContext ctx) {
        this.current.setMessage(ctx.getText());
    }
}
