package com.example.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerExample {

    public static class Producer implements Runnable {

        private final BlockingQueue<Integer> queue;

        public Producer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("{" + Thread.currentThread().getName() + "}, Add item: " + i);
                    queue.put(i++);
                    Thread.sleep((long) (Math.random() * 5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static class Consumer implements Runnable {

        private final BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    int item = queue.take();
                    System.out.println("{" + Thread.currentThread().getName() + "}, Pop item: " + item);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {

        ExecutorService producers = Executors.newFixedThreadPool(10);
        ExecutorService consumers = Executors.newFixedThreadPool(10);

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        for (int i = 0; i < 10; i++) {
            producers.submit(new Producer(queue));
            consumers.submit(new Consumer(queue));
        }
    }
}
