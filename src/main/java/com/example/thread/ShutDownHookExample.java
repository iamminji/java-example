package com.example.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShutDownHookExample {

    private static final Logger LOG = LoggerFactory.getLogger(ShutDownHookExample.class);

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("shutdown in 5s ");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignore) {
            }
        }));

        Executors.newSingleThreadScheduledExecutor().
                scheduleAtFixedRate(() -> System.out.println("."), 0, 1, TimeUnit.SECONDS);

    }
}
