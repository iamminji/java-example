package org.example;

public class ThreadExample {

    public static void main(String[] args) throws InterruptedException {

        // Thread[#22,Thread-0,5,main]
        var pThread = Thread.ofPlatform()
            .unstarted(() -> System.out.println(Thread.currentThread()));

        pThread.start();
        pThread.join();


        // VirtualThread[#23]/runnable@ForkJoinPool-1-worker-1
        var vThread = Thread.ofVirtual()
            .unstarted(() -> System.out.println(Thread.currentThread()));

        vThread.start();
        vThread.join();

        // class java.lang.VirtualThread
        System.out.println(vThread.getClass().getName());
    }
}
