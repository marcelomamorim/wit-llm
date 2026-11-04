/*
 * @test
 * @summary Behavioral tests for ReentrantLock.lock() using only public observable state
 * @run main ReentrantLockWitupTest
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testLockAcquiresAndHoldsLock();
        testReentrantLockIncrementsHoldCount();
        testFairLockAcquisition();
        testLockBlocksOtherThreadsUntilReleased();
        System.out.println("PASSED");
    }

    static void testLockAcquiresAndHoldsLock() {
        ReentrantLock lock = new ReentrantLock();
        check(!lock.isLocked(), "new lock must not be locked");
        lock.lock();
        try {
            check(lock.isLocked(), "lock() must make the lock held");
            check(lock.isHeldByCurrentThread(), "lock() must be owned by current thread");
            check(lock.getHoldCount() == 1, "hold count after one lock() must be 1");
        } finally {
            lock.unlock();
        }
        check(!lock.isLocked(), "lock must be free after unlock()");
    }

    static void testReentrantLockIncrementsHoldCount() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.lock();
        try {
            check(lock.getHoldCount() == 2, "reentrant lock() must increase hold count to 2");
            check(lock.isHeldByCurrentThread(), "reentrant lock must still be owned");
        } finally {
            lock.unlock();
            lock.unlock();
        }
        check(lock.getHoldCount() == 0, "hold count must be 0 after balanced unlocks");
        check(!lock.isLocked(), "lock must be free after balanced unlocks");
    }

    static void testFairLockAcquisition() {
        ReentrantLock fair = new ReentrantLock(true);
        check(fair.isFair(), "lock must report fair");
        fair.lock();
        try {
            check(fair.isHeldByCurrentThread(), "fair lock() must be owned by current thread");
            check(fair.getHoldCount() == 1, "fair lock() hold count must be 1");
        } finally {
            fair.unlock();
        }
        check(!fair.isLocked(), "fair lock must be free after unlock()");
    }

    static void testLockBlocksOtherThreadsUntilReleased() throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        final boolean[] tryResultWhileHeld = new boolean[1];
        final boolean[] acquiredAfterRelease = new boolean[1];

        lock.lock();
        try {
            Thread t1 = new Thread(() -> {
                tryResultWhileHeld[0] = lock.tryLock();
                if (tryResultWhileHeld[0]) {
                    lock.unlock();
                }
            });
            t1.start();
            t1.join();
            check(!tryResultWhileHeld[0], "other thread must not acquire lock held by lock()");
        } finally {
            lock.unlock();
        }

        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                acquiredAfterRelease[0] = true;
            } finally {
                lock.unlock();
            }
        });
        t2.start();
        t2.join();
        check(acquiredAfterRelease[0], "other thread must acquire lock after release");
    }
}
