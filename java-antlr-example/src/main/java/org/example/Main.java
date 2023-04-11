package org.example;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.example.log.LogEntry;
import org.example.log.LogListener;
import org.example.parser.LoggerLexer;
import org.example.parser.LoggerParser;

public class Main {

    public static void main(String[] args) {
        String logLines = "2018-May-05 14:20:21 DEBUG entering awesome method\r\n";
        LoggerLexer serverLogLexer = new LoggerLexer(CharStreams.fromString(logLines));
        CommonTokenStream tokens = new CommonTokenStream(serverLogLexer);
        LoggerParser logParser = new LoggerParser(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();
        LogListener logWalker = new LogListener();
        walker.walk(logWalker, logParser.log());

        LogEntry error = logWalker.getEntries().get(0);
        System.out.println(error);
    }
}
