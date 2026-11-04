/*
 * @test
 * @summary Verifies observable behavior of ReentrantReadWriteLock.ReadLock.lock()
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testLockIncrementsReadCount() {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock rl = rwl.readLock();
        rl.lock();
        try {
            check(rwl.getReadLockCount() == 1, "read count should be 1 after lock()");
        } finally {
            rl.unlock();
        }
        check(rwl.getReadLockCount() == 0, "read count should be 0 after unlock()");
    }

    static void testReentrantLockCount() {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock rl = rwl.readLock();
        rl.lock();
        rl.lock();
        try {
            check(rwl.getReadLockCount() == 1, "reentrant readers of one thread count as one reader");
            check(rl.tryLock(), "same thread should reacquire read lock");
            rl.unlock();
        } finally {
            rl.unlock();
            rl.unlock();
        }
        check(rwl.getReadLockCount() == 0, "all read holds should be released");
    }

    static void testWriteTryLockFailsWhileReadHeld() {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock rl = rwl.readLock();
        rl.lock();
        try {
            check(!rwl.writeLock().tryLock(), "write tryLock must fail while read lock is held");
        } finally {
            rl.unlock();
        }
        check(rwl.writeLock().tryLock(), "write tryLock should succeed after read unlock");
        rwl.writeLock().unlock();
    }

    static void testOtherThreadReadLockDoesNotBlock() throws InterruptedException {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock rl = rwl.readLock();
        final boolean[] acquired = new boolean[1];
        rl.lock();
        try {
            Thread t = new Thread(() -> {
                rwl.readLock().lock();
                try {
                    acquired[0] = true;
                } finally {
                    rwl.readLock().unlock();
                }
            });
            t.start();
            t.join(10000);
            check(!t.isAlive(), "second reader thread should not block");
            check(acquired[0], "second reader should acquire read lock");
            check(rwl.getReadLockCount() == 1, "only main thread holds read lock now");
        } finally {
            rl.unlock();
        }
    }

    static void testFairLockAcquire() {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
        ReentrantReadWriteLock.ReadLock rl = rwl.readLock();
        rl.lock();
        try {
            check(rwl.getReadLockCount() == 1, "fair read lock should be counted");
        } finally {
            rl.unlock();
        }
        check(rwl.getReadLockCount() == 0, "fair read lock should be released");
    }

    public static void main(String[] args) throws Exception {
        testLockIncrementsReadCount();
        testReentrantLockCount();
        testWriteTryLockFailsWhileReadHeld();
        testOtherThreadReadLockDoesNotBlock();
        testFairLockAcquire();
    }
}
