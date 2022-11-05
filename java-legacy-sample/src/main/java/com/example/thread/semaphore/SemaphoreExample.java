package com.example.thread.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SemaphoreExample {

    private Semaphore semaphore;

    public SemaphoreExample(int limit) {
        this.semaphore = new Semaphore(limit);
    }

    public boolean tryLogin() {
        return semaphore.tryAcquire();
    }

    public void logout() {
        semaphore.release();
    }

    public int availableSlots() {
        return semaphore.availablePermits();
    }

    public static void main(String[] args) throws InterruptedException {
        int limit = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(limit);
        SemaphoreExample example = new SemaphoreExample(limit);
        IntStream.range(0, limit)
                .forEach(i -> executorService.execute(example::tryLogin));
        Thread.sleep(300);

        System.out.println("---------- no release ------------");
        System.out.println(example.availableSlots());
        System.out.println(example.tryLogin());

        System.out.println("---------- release ------------");
        example.logout();

        System.out.println(example.availableSlots());
        System.out.println(example.tryLogin());

        executorService.shutdown();
    }
}
