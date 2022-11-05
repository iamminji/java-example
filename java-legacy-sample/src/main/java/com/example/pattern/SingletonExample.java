package com.example.pattern;

import java.util.concurrent.CountDownLatch;

public class SingletonExample {

    public static class SyncTickets {
        private static int ticket;
        private static SyncTickets instance;

        private SyncTickets() {
        }

        public synchronized static SyncTickets getInstance() {
            // 멀티 쓰레드 환경에서 싱글톤 인스턴스를 하나만 만들기 위해 synchronized 키워드를 넣는다.
            // 단 성능상 문제가 있다.
            if (instance == null) {
                System.out.println("Call Only Once");
                instance = new SyncTickets();
            }
            return instance;
        }

        public synchronized int getTicket() {
            return ticket++;
        }
    }

    public static class LazyTickets {
        private static int ticket;

        private LazyTickets() {
        }

        // JVM에게 객체의 초기화를 떠님기는 방식으로, 멀티스레드 환경에서도 객체의 단일성을 보장할 수 있다.
        // Class 를 로딩하고 초기화하는건 JVM 의 영역이고 Thread Safe 를 보장한다.
        public static LazyTickets getInstance() {
            return LazyHolder.INSTANCE;
        }

        private static class LazyHolder {
            private static final LazyTickets INSTANCE = new LazyTickets();
        }

        public synchronized int getTicket() {
            return ticket++;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // 표준 출력에서 synchronized 적용된 싱글톤 클래스를 먼저 보려고 임의로 넣음
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SyncTickets tickets = SyncTickets.getInstance();
                System.out.println(tickets.hashCode());
                System.out.println("Sync-> " + Thread.currentThread().getName() + ":" + tickets.getTicket());
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                LazyTickets tickets = LazyTickets.getInstance();
                System.out.println(tickets.hashCode());
                System.out.println("Lazy-> " + Thread.currentThread().getName() + ":" + tickets.getTicket());
            }).start();
        }
    }
}
