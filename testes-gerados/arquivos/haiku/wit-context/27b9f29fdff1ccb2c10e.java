/*
 * @test
 * @summary WITUP-derived behavioral checks for ReentrantReadWriteLock.WriteLock.lock()
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        testLockAcquiresWriteLock();
        testLockIsReentrant();
        testLockOnFairLock();
        testOtherThreadCannotReadWhileWriteHeld();
    }

    static void testLockAcquiresWriteLock() {
        ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock w = rw.writeLock();
        w.lock();
        try {
            check(rw.isWriteLocked(), "write lock should be held after lock()");
            check(rw.isWriteLockedByCurrentThread(), "current thread should own write lock");
            check(rw.getWriteHoldCount() == 1, "hold count should be 1");
            check(w.isHeldByCurrentThread(), "WriteLock should be held by current thread");
        } finally {
            w.unlock();
        }
        check(!rw.isWriteLocked(), "write lock should be released after unlock()");
    }

    static void testLockIsReentrant() {
        ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock w = rw.writeLock();
        w.lock();
        w.lock();
        try {
            check(rw.getWriteHoldCount() == 2, "hold count should be 2 after two lock() calls");
        } finally {
            w.unlock();
            w.unlock();
        }
        check(rw.getWriteHoldCount() == 0, "hold count should be 0 after matching unlocks");
        check(!rw.isWriteLocked(), "write lock should be free");
    }

    static void testLockOnFairLock() {
        ReentrantReadWriteLock rw = new ReentrantReadWriteLock(true);
        ReentrantReadWriteLock.WriteLock w = rw.writeLock();
        w.lock();
        try {
            check(rw.isWriteLockedByCurrentThread(), "fair write lock should be owned after lock()");
            check(rw.getWriteHoldCount() == 1, "fair write hold count should be 1");
        } finally {
            w.unlock();
        }
        check(!rw.isWriteLocked(), "fair write lock should be released");
    }

    static void testOtherThreadCannotReadWhileWriteHeld() throws InterruptedException {
        ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock w = rw.writeLock();
        final boolean[] acquired = new boolean[1];
        w.lock();
        try {
            Thread t = new Thread(() -> {
                acquired[0] = rw.readLock().tryLock();
            });
            t.start();
            t.join();
            check(!acquired[0], "other thread must not acquire read lock while write lock is held");
        } finally {
            w.unlock();
        }
    }
}
