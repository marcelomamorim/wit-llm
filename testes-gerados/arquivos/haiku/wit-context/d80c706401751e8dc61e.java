/*
 * @test
 * @summary Witup-generated checks for ReentrantReadWriteLock write lock unlock behavior
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) throws Exception {
        testUnlockWithoutHoldThrowsIMSE(false);
        testUnlockWithoutHoldThrowsIMSE(true);
        testReentrantUnlockCounts();
        testUnlockFromOtherThreadThrowsIMSE();
        testUnlockWhileOnlyReadLockHeld();
    }

    static void testUnlockWithoutHoldThrowsIMSE(boolean fair) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(fair);
        boolean thrown = false;
        try {
            lock.writeLock().unlock();
        } catch (IllegalMonitorStateException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalMonitorStateException when unlocking unheld write lock");
        }
        if (lock.isWriteLocked()) {
            throw new RuntimeException("write lock state changed by failed unlock");
        }
    }

    static void testReentrantUnlockCounts() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        lock.writeLock().lock();
        if (lock.getWriteHoldCount() != 2) {
            throw new RuntimeException("expected write hold count 2, got " + lock.getWriteHoldCount());
        }
        lock.writeLock().unlock();
        if (lock.getWriteHoldCount() != 1 || !lock.isWriteLocked()) {
            throw new RuntimeException("expected still write locked with hold count 1");
        }
        lock.writeLock().unlock();
        if (lock.isWriteLocked()) {
            throw new RuntimeException("expected write lock released");
        }
        boolean thrown = false;
        try {
            lock.writeLock().unlock();
        } catch (IllegalMonitorStateException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalMonitorStateException on extra unlock");
        }
    }

    static void testUnlockFromOtherThreadThrowsIMSE() throws InterruptedException {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        final boolean[] caught = new boolean[1];
        lock.writeLock().lock();
        Thread t = new Thread(() -> {
            try {
                lock.writeLock().unlock();
            } catch (IllegalMonitorStateException e) {
                caught[0] = true;
            }
        });
        t.start();
        t.join();
        if (!caught[0]) {
            throw new RuntimeException("expected IllegalMonitorStateException from non-owner thread");
        }
        if (!lock.isWriteLockedByCurrentThread()) {
            throw new RuntimeException("owner thread lost write lock");
        }
        lock.writeLock().unlock();
        if (lock.isWriteLocked()) {
            throw new RuntimeException("expected write lock released by owner");
        }
    }

    static void testUnlockWhileOnlyReadLockHeld() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        boolean thrown = false;
        try {
            lock.writeLock().unlock();
        } catch (IllegalMonitorStateException expected) {
            thrown = true;
        } finally {
            lock.readLock().unlock();
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalMonitorStateException when only read lock is held");
        }
        if (lock.getReadLockCount() != 0) {
            throw new RuntimeException("read lock not released");
        }
    }
}
