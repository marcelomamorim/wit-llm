/* @test    @summary Test ReentrantReadWriteLock lock method for basic locking behavior    @run main ReentrantReadWriteLockWitupTest */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        testLockBasic();
        testLockMultipleThreads();
    }

    private static void testLockBasic() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        try {
            lock.lock();
            // If lock.lock() does not throw, lock is acquired
            System.out.println("testLockBasic: Lock acquired successfully.");
        } finally {
            lock.unlock();
        }
    }

    private static void testLockMultipleThreads() {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Thread 1: Lock acquired.");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // ignore
                }
            } finally {
                lock.unlock();
                System.out.println("Thread 1: Lock released.");
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // ignore
            }
            lock.lock();
            try {
                System.out.println("Thread 2: Lock acquired.");
            } finally {
                lock.unlock();
                System.out.println("Thread 2: Lock released.");
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
            System.out.println("testLockMultipleThreads: Completed.");
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
