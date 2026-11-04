/*
 * @test
 * @summary Behavioral checks for ReentrantLock.lock(): acquisition, reentrancy, mutual exclusion and fair construction
 * @run main ReentrantLockWitupTest
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {

    public static void main(String[] args) throws Exception {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            check(lock.isHeldByCurrentThread(), "lock() must acquire the lock for the current thread");
            check(lock.getHoldCount() == 1, "hold count must be 1 after first lock()");
            check(lock.isLocked(), "lock must be reported as locked after lock()");

            lock.lock();
            check(lock.getHoldCount() == 2, "reentrant lock() must increase hold count to 2");
            lock.unlock();
            check(lock.getHoldCount() == 1, "unlock() after reentrant lock() must decrease hold count to 1");
        } finally {
            lock.unlock();
        }
        check(!lock.isLocked(), "lock must be free after releasing all holds");
        check(!lock.isHeldByCurrentThread(), "current thread must not hold the lock after full release");

        lock.lock();
        final boolean[] acquired = new boolean[1];
        Thread other = new Thread(() -> {
            boolean got = lock.tryLock();
            acquired[0] = got;
            if (got) {
                lock.unlock();
            }
        });
        other.start();
        other.join();
        check(!acquired[0], "another thread must not acquire a lock held by the current thread");
        lock.unlock();

        ReentrantLock fair = new ReentrantLock(true);
        fair.lock();
        try {
            check(fair.isHeldByCurrentThread(), "fair lock() must acquire the lock for the current thread");
            check(fair.getHoldCount() == 1, "fair lock hold count must be 1 after lock()");
        } finally {
            fair.unlock();
        }
        check(!fair.isLocked(), "fair lock must be free after unlock()");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
