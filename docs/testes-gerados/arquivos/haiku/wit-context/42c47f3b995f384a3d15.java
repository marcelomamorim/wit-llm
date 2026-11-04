/*
 * @test
 * @summary ReadLock.lock() acquires a shared hold observable through public API
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        checkShared(new ReentrantReadWriteLock(), "non-fair");
        checkShared(new ReentrantReadWriteLock(true), "fair");
    }

    private static void checkShared(ReentrantReadWriteLock rw, String mode) {
        rw.readLock().lock();
        try {
            check(rw.getReadLockCount() == 1, mode + ": read count after one lock");
            check(!rw.isWriteLocked(), mode + ": write lock must not be held");
            check(!rw.writeLock().tryLock(), mode + ": write tryLock must fail while read lock is held");

            rw.readLock().lock();
            try {
                check(rw.getReadLockCount() == 2, mode + ": read count after reentrant lock");
            } finally {
                rw.readLock().unlock();
            }
            check(rw.getReadLockCount() == 1, mode + ": read count after reentrant unlock");
        } finally {
            rw.readLock().unlock();
        }
        check(rw.getReadLockCount() == 0, mode + ": read count after unlock");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
