package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceExample {

    public static void main(String[] args) {
        ExecutorService executor1 = Executors.newVirtualThreadPerTaskExecutor(); // New method
        ExecutorService executor2 = Executors.newFixedThreadPool(30); // Old method
    }
}
