/*
 * @test
 * @summary Behavioral tests for ReentrantLock.unlock() on JDK 11
 * @run main ReentrantLockWitupTest
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {

    public static void main(String[] args) throws Exception {
        testUnlockReleasesHeldLock();
        testReentrantUnlockDecrementsHoldCount();
        testUnlockWithoutHoldingThrows();
        testUnlockFromOtherThreadThrowsAndKeepsOwnership();
        testUnlockMakesLockAvailableToOtherThread();
        testFairLockUnlock();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testUnlockReleasesHeldLock() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        check(lock.isHeldByCurrentThread(), "lock should be held before unlock");
        lock.unlock();
        check(!lock.isLocked(), "lock should be free after unlock");
        check(!lock.isHeldByCurrentThread(), "current thread should not hold lock after unlock");
        check(lock.getHoldCount() == 0, "hold count should be 0 after unlock");
    }

    static void testReentrantUnlockDecrementsHoldCount() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.lock();
        check(lock.getHoldCount() == 2, "hold count should be 2");
        lock.unlock();
        check(lock.getHoldCount() == 1, "hold count should be 1 after first unlock");
        check(lock.isLocked(), "lock should still be locked after first unlock");
        check(lock.isHeldByCurrentThread(), "current thread should still hold lock");
        lock.unlock();
        check(lock.getHoldCount() == 0, "hold count should be 0 after second unlock");
        check(!lock.isLocked(), "lock should be free after second unlock");
    }

    static void testUnlockWithoutHoldingThrows() {
        ReentrantLock lock = new ReentrantLock();
        boolean thrown = false;
        try {
            lock.unlock();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        check(thrown, "unlock without holding the lock should throw IllegalMonitorStateException");
        check(!lock.isLocked(), "lock should remain free");
    }

    static void testUnlockFromOtherThreadThrowsAndKeepsOwnership() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            final boolean[] thrown = new boolean[1];
            Thread t = new Thread(() -> {
                try {
                    lock.unlock();
                } catch (IllegalMonitorStateException e) {
                    thrown[0] = true;
                }
            });
            t.start();
            t.join();
            check(thrown[0], "unlock from non-owner thread should throw IllegalMonitorStateException");
            check(lock.isLocked(), "lock should still be locked after failed foreign unlock");
            check(lock.isHeldByCurrentThread(), "owner should still hold lock");
            check(lock.getHoldCount() == 1, "hold count should be unchanged");
        } finally {
            lock.unlock();
        }
    }

    static void testUnlockMakesLockAvailableToOtherThread() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
        final boolean[] acquired = new boolean[1];
        Thread t = new Thread(() -> {
            acquired[0] = lock.tryLock();
            if (acquired[0]) {
                lock.unlock();
            }
        });
        t.start();
        t.join();
        check(acquired[0], "other thread should acquire lock after unlock");
    }

    static void testFairLockUnlock() {
        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        lock.unlock();
        check(!lock.isLocked(), "fair lock should be free after unlock");
        check(lock.getHoldCount() == 0, "fair lock hold count should be 0 after unlock");
    }
}
