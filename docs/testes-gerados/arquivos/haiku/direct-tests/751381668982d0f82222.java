/*
 * @test
 * @summary Checks ReentrantReadWriteLock.hasQueuedThread for free, unstarted, queued writer and queued reader threads
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) throws Exception {
        testFreeLockHasNoQueuedThreads();
        testUnstartedThreadNotQueued();
        testWriterQueued(false);
        testWriterQueued(true);
        testReaderQueued(false);
        testReaderQueued(true);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void waitUntilQueued(ReentrantReadWriteLock lock, Thread t) throws InterruptedException {
        long deadline = System.nanoTime() + 10_000_000_000L;
        while (!lock.hasQueuedThread(t)) {
            if (System.nanoTime() > deadline) {
                throw new RuntimeException("thread was never reported as queued");
            }
            Thread.sleep(1);
        }
    }

    static void testFreeLockHasNoQueuedThreads() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        check(!lock.hasQueuedThread(Thread.currentThread()),
              "current thread should not be queued on a free lock");
    }

    static void testUnstartedThreadNotQueued() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        try {
            Thread t = new Thread(() -> { });
            check(!lock.hasQueuedThread(t), "unstarted thread should not be queued");
        } finally {
            lock.writeLock().unlock();
        }
    }

    static void testWriterQueued(boolean fair) throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(fair);
        lock.writeLock().lock();
        Thread t = new Thread(() -> {
            lock.writeLock().lock();
            lock.writeLock().unlock();
        });
        t.start();
        waitUntilQueued(lock, t);
        check(lock.hasQueuedThread(t), "blocked writer should be queued");
        check(!lock.hasQueuedThread(Thread.currentThread()),
              "lock holder should not be reported as queued");
        lock.writeLock().unlock();
        t.join();
        check(!lock.hasQueuedThread(t), "terminated writer should no longer be queued");
    }

    static void testReaderQueued(boolean fair) throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(fair);
        lock.writeLock().lock();
        Thread t = new Thread(() -> {
            lock.readLock().lock();
            lock.readLock().unlock();
        });
        t.start();
        waitUntilQueued(lock, t);
        check(lock.hasQueuedThread(t), "blocked reader should be queued");
        lock.writeLock().unlock();
        t.join();
        check(!lock.hasQueuedThread(t), "terminated reader should no longer be queued");
    }
}
