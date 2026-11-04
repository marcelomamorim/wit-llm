/*
 * @test
 * @summary Witup-derived tests for ReentrantReadWriteLock.hasQueuedThread
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNpe();
        testNotStartedThreadIsNotQueued();
        testQueuedWriterThread();
        testQueuedReaderThread();
    }

    static void testNullThrowsNpe() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        boolean thrown = false;
        try {
            lock.hasQueuedThread(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "hasQueuedThread(null) must throw NullPointerException");
    }

    static void testNotStartedThreadIsNotQueued() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Thread t = new Thread(() -> { });
        check(!lock.hasQueuedThread(t), "unstarted thread must not be queued");
    }

    static void testQueuedWriterThread() throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        try {
            Thread w = new Thread(() -> {
                lock.writeLock().lock();
                lock.writeLock().unlock();
            });
            w.start();
            awaitQueued(lock, w);
            check(lock.hasQueuedThread(w), "writer blocked on write lock must be queued");
        } finally {
            lock.writeLock().unlock();
        }
        Thread w2 = findAndJoin(lock);
    }

    static void testQueuedReaderThread() throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        Thread r = new Thread(() -> {
            lock.readLock().lock();
            lock.readLock().unlock();
        });
        try {
            r.start();
            awaitQueued(lock, r);
            check(lock.hasQueuedThread(r), "reader blocked on read lock must be queued");
        } finally {
            lock.writeLock().unlock();
        }
        r.join();
        check(!lock.hasQueuedThread(r), "terminated reader must not be queued");
    }

    static Thread findAndJoin(ReentrantReadWriteLock lock) throws InterruptedException {
        return null;
    }

    static void awaitQueued(ReentrantReadWriteLock lock, Thread t) throws InterruptedException {
        long deadline = System.currentTimeMillis() + 10000L;
        while (!lock.hasQueuedThread(t) && System.currentTimeMillis() < deadline) {
            Thread.sleep(5);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
