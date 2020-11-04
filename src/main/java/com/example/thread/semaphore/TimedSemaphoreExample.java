package com.example.thread.semaphore;

import org.apache.commons.lang3.concurrent.TimedSemaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 참고 https://www.baeldung.com/java-semaphore
 */
public class TimedSemaphoreExample {

    private TimedSemaphore timedSemaphore;

    public TimedSemaphoreExample(long period, int slotLimit) {
        this.timedSemaphore = new TimedSemaphore(period, TimeUnit.SECONDS, slotLimit);
    }

    public boolean tryAdd() {
        return timedSemaphore.tryAcquire();
    }

    public int availableSlots() {
        return timedSemaphore.getAvailablePermits();
    }

    public static void main(String[] args) throws InterruptedException {

        int limit = 3;

        ExecutorService executorService = Executors.newFixedThreadPool(limit);
        TimedSemaphoreExample example = new TimedSemaphoreExample(1, limit);

        IntStream.range(0, limit)
                .forEach(i -> executorService.execute(example::tryAdd));

        executorService.shutdown();

        Thread.sleep(1000);

        // should be 0
        System.out.println(example.availableSlots());
        System.out.println(example.tryAdd());
        Thread.sleep(5000);
        // should be false
        System.out.println(example.availableSlots());
        System.out.println(example.tryAdd());

    }
}
