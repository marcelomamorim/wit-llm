/*
 * @test
 * @summary Witup-generated checks for ReentrantLock.unlock() observable behavior
 * @run main ReentrantLockWitupTest
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {

    public static void main(String[] args) throws Exception {
        testUnlockWithoutHoldThrowsIllegalMonitorState();
        testUnlockReleasesSingleHold();
        testReentrantUnlockDecrementsHoldCount();
        testUnlockByNonOwnerThreadThrowsIllegalMonitorState();
        testUnlockOnFairLock();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testUnlockWithoutHoldThrowsIllegalMonitorState() {
        ReentrantLock lock = new ReentrantLock();
        boolean thrown = false;
        try {
            lock.unlock();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        check(thrown, "unlock() without hold must throw IllegalMonitorStateException");
        check(!lock.isLocked(), "lock must remain unlocked after failed unlock");
    }

    static void testUnlockReleasesSingleHold() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        check(lock.isHeldByCurrentThread(), "lock() must acquire the lock");
        lock.unlock();
        check(!lock.isLocked(), "unlock() must release the single hold");
        check(!lock.isHeldByCurrentThread(), "current thread must not hold lock after unlock()");
        check(lock.getHoldCount() == 0, "hold count must be 0 after unlock()");
    }

    static void testReentrantUnlockDecrementsHoldCount() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.lock();
        check(lock.getHoldCount() == 2, "hold count must be 2 after two lock() calls");
        lock.unlock();
        check(lock.getHoldCount() == 1, "first unlock() must decrement hold count to 1");
        check(lock.isHeldByCurrentThread(), "lock must still be held after first unlock()");
        lock.unlock();
        check(lock.getHoldCount() == 0, "second unlock() must bring hold count to 0");
        check(!lock.isLocked(), "lock must be released after matching unlock() calls");
    }

    static void testUnlockByNonOwnerThreadThrowsIllegalMonitorState() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        final boolean[] thrown = new boolean[1];
        Thread other = new Thread(() -> {
            try {
                lock.unlock();
            } catch (IllegalMonitorStateException e) {
                thrown[0] = true;
            }
        });
        other.start();
        other.join();
        check(thrown[0], "unlock() by non-owner thread must throw IllegalMonitorStateException");
        check(lock.isHeldByCurrentThread(), "owner must still hold lock after non-owner unlock attempt");
        lock.unlock();
        check(!lock.isLocked(), "owner unlock() must release the lock");
    }

    static void testUnlockOnFairLock() {
        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        lock.unlock();
        check(!lock.isLocked(), "fair lock must be released after unlock()");
        boolean thrown = false;
        try {
            lock.unlock();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        check(thrown, "unlock() on fair lock without hold must throw IllegalMonitorStateException");
    }
}
