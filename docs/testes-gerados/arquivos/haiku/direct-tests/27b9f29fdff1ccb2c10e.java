/*
 * @test
 * @summary Verifies that WriteLock.lock() acquires the write lock, supports reentrancy, blocks other threads, and works in fair mode
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) throws Exception {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock wl = rwl.writeLock();

        wl.lock();
        check(rwl.isWriteLocked(), "write lock should be held after lock()");
        check(rwl.isWriteLockedByCurrentThread(), "current thread should own the write lock");
        check(rwl.getWriteHoldCount() == 1, "hold count should be 1 after one lock()");

        wl.lock();
        check(rwl.getWriteHoldCount() == 2, "hold count should be 2 after reentrant lock()");

        wl.unlock();
        wl.unlock();
        check(!rwl.isWriteLocked(), "write lock should be free after matching unlocks");
        check(rwl.getWriteHoldCount() == 0, "hold count should be 0 after full release");

        wl.lock();
        final boolean[] acquired = new boolean[1];
        Thread other = new Thread(() -> acquired[0] = rwl.writeLock().tryLock());
        other.start();
        other.join();
        check(!acquired[0], "another thread must not acquire the write lock while it is held");
        wl.unlock();

        ReentrantReadWriteLock fair = new ReentrantReadWriteLock(true);
        fair.writeLock().lock();
        check(fair.isWriteLockedByCurrentThread(), "fair write lock should be owned after lock()");
        fair.writeLock().unlock();
        check(!fair.isWriteLocked(), "fair write lock should be free after unlock()");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
