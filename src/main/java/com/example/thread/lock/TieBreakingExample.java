package com.example.thread.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Tie breaking 을 이용하여 Dead Lock 을 피하는 예제
 */
public class TieBreakingExample {

    private static final Logger LOG = LoggerFactory.getLogger(TieBreakingExample.class);
    private static final Object tieLock = new Object();

    static class Account {
        private final String name;
        private int balance;

        public Account(String name, int balance) {
            this.name = name;
            this.balance = balance;
            LOG.info("Initialize {}", this);
        }

        public String getName() {
            return name;
        }

        public int getBalance() {
            return balance;
        }

        public void withdraw(int amount) {
            LOG.debug("WithDraw amount={}, balance={}", amount, balance);
            balance -= amount;
        }

        public void deposit(int amount) {
            LOG.debug("Deposit amount={}, balance={}", amount, balance);
            balance += amount;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "name='" + name + '\'' +
                    ", balance=" + balance +
                    '}';
        }
    }

    public void transferMoney(final Account fromAccount, final Account toAccount, final int amount) {
        if (fromAccount.getBalance() < amount) {
            // throw exception;
            LOG.error("Account has no money! current balance={}, amount={}", fromAccount.getBalance(), amount);
        } else {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            LOG.info("Result, {}, {}", fromAccount, toAccount);
        }
    }

    public void transfer(final Account fromAccount, final Account toAccount, final int amount) {
        synchronized (fromAccount) {
            synchronized (toAccount) {
                transferMoney(fromAccount, toAccount, amount);
            }
        }
    }

    public void transferByTie(final Account fromAccount, final Account toAccount, final int amount) {
        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);

        if (fromHash < toHash) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    transferMoney(fromAccount, toAccount, amount);
                }
            }
        } else if (fromHash > toHash) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    transferMoney(fromAccount, toAccount, amount);
                }
            }
        } else {
            // 타이 브레이킹 락을 확보하면 두 개의 락을 임의의 순서로 확보하는 위험한 작업을
            // 특정 순간에 하나의 쓰레드에서만 할 수 있게 된다.
            // hashCode 값이 계속 겹치면 여기가 병목이 될 수도 있다.
            synchronized (tieLock) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        transferMoney(fromAccount, toAccount, amount);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threads = Executors.newFixedThreadPool(10);

        Account fromAccount = new Account("From", 100);
        Account toAccount = new Account("To", 50);

        Thread t1 = new Thread(() -> {
            TieBreakingExample obj = new TieBreakingExample();
            // dead lock
            // obj.transfer(fromAccount, toAccount, 20);
            obj.transferByTie(fromAccount, toAccount, 20);
        });
        Thread t2 = new Thread(() -> {
            TieBreakingExample obj = new TieBreakingExample();
            // dead lock
            // obj.transfer(toAccount, fromAccount, 30);
            obj.transferByTie(toAccount, fromAccount, 30);
        });
        threads.execute(t1);
        threads.execute(t2);

        // 데드락 없을 땐 셧다운 된다.
        threads.shutdown();

        if (!threads.awaitTermination(10, TimeUnit.SECONDS)) {
            // 데드락 발생 시 강제 종료
            // 위에서 obj.transfer 를 사용하면 데드락에 빠져 여기까지 오게 된다.
            LOG.error("ShutDown!");
            threads.shutdownNow();
        }
    }
}
