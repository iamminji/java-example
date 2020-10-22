package com.example.thread;

import java.util.concurrent.*;

public class MultiThreadStopWIthPoison {

    interface Person {
    }

    static class PoisonPill implements Person {

    }

    static class Student implements Person {
        final String name;
        final int number;

        public Student(String name, int number) {
            this.name = name;
            this.number = number;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", number=" + number +
                    '}';
        }
    }

    static class Producer implements Runnable {

        final BlockingQueue<Person> queue;

        public Producer(BlockingQueue<Person> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                try {
                    queue.put(new Student("Java", 1));
                    queue.put(new Student("Python", 2));
                    queue.put(new Student("Golang", 3));
                    // Poison pill 을 큐에 넣는다.
                    queue.put(new PoisonPill());
                    queue.put(new Student("Javascript", 99));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            } finally {
                System.out.println("Producer, {" + Thread.currentThread().getName() + "}, Stop...");
            }
        }
    }

    static class Consumer implements Runnable {

        final BlockingQueue<Person> queue;

        public Consumer(BlockingQueue<Person> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        Person p = queue.take();
                        if (p instanceof PoisonPill) {
                            // Poison pill 발견하면 consumer while 을 break 한다.
                            // FIXME equal 로 특정 값을 비교하는게 더 나은걸까?
                            System.out.println("Consumer, {" + Thread.currentThread().getName() + "}, Found poison!! It Should stop, queueSize=" + queue.size());
                            break;
                        }
                        System.out.println("Consumer, {" + Thread.currentThread().getName() + "}, Take =" + p + ", queueSize=" + queue.size());
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

        BlockingQueue<Person> queue = new LinkedBlockingQueue<>(100);
        ExecutorService producers = Executors.newSingleThreadExecutor();
        ExecutorService consumers = Executors.newSingleThreadExecutor();

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        producers.execute(producer);
        consumers.execute(consumer);

        producers.shutdown();
        consumers.shutdown();
    }
}
