package com.example.concurrency;

import java.util.concurrent.*;

/**
 * Executor shutdown 을 사용하여 종료하는 예
 */
public class MultiThreadStopShutdown {

    static class Producer implements Runnable {

        final BlockingQueue<Integer> queue;

        public Producer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            int i = 0;
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        System.out.println("Prodcuer, {" + Thread.currentThread().getName() + "}, Put i=" + i + ", queueSize=" + queue.size());
                        queue.put(i++);
                        Thread.sleep((long) (Math.random() * 5000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }

                }
            } finally {
                System.out.println("Producer, {" + Thread.currentThread().getName() + "}, Stop...");
            }
        }
    }

    static class Consumer implements Runnable {

        final BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        int i = queue.take();
                        System.out.println("Consumer, {" + Thread.currentThread().getName() + "}, Take i=" + i + ", queueSize=" + queue.size());
                        Thread.sleep((long) (Math.random() * 10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();

                    }
                }
            } finally {
                System.out.println("Consumer, {" + Thread.currentThread().getName() + "}, Stop...");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(20);

        ExecutorService producers = Executors.newFixedThreadPool(10);
        ExecutorService consumers = Executors.newFixedThreadPool(10);

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        for (int i = 0; i < 10; i++) {
            producers.execute(producer);
            consumers.execute(consumer);
        }

        producers.shutdown();
        consumers.shutdown();

        // 20 초 정도 기다렸는데도 producer 가 종료를 안한다면 shutdownNow 를 호출해서 강제 종료 해준다.
        if (!producers.awaitTermination(20, TimeUnit.SECONDS)) {
            producers.shutdownNow();
        }

        // 30 초 정도 기다렸는데도 producer 가 종료를 안한다면 shutdownNow 를 호출해서 강제 종료 해준다.
        if (!consumers.awaitTermination(30, TimeUnit.SECONDS)) {
            consumers.shutdownNow();
        }
    }
}
