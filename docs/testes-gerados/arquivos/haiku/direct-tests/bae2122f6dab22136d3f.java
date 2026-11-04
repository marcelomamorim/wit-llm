/*
 * @test
 * @summary Verifies ReentrantLock.hasQueuedThread reports only threads waiting in the lock queue
 * @run main ReentrantLockWitupTest
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {
    public static void main(String[] args) throws Exception {
        run(new ReentrantLock());
        run(new ReentrantLock(true));
    }

    static void run(ReentrantLock lock) throws Exception {
        Thread current = Thread.currentThread();

        if (lock.hasQueuedThread(current)) {
            throw new RuntimeException("current thread reported as queued on idle lock");
        }

        Thread t = new Thread(() -> {
            lock.lock();
            lock.unlock();
        });

        if (lock.hasQueuedThread(t)) {
            throw new RuntimeException("unstarted thread reported as queued");
        }

        lock.lock();
        try {
            t.start();
            long deadline = System.nanoTime() + 10_000_000_000L;
            while (!lock.hasQueuedThread(t)) {
                if (System.nanoTime() > deadline) {
                    throw new RuntimeException("waiting thread never reported as queued");
                }
                Thread.sleep(5);
            }
            if (lock.hasQueuedThread(current)) {
                throw new RuntimeException("lock holder reported as queued");
            }
        } finally {
            lock.unlock();
        }

        t.join(10000);
        if (t.isAlive()) {
            throw new RuntimeException("waiting thread did not complete");
        }
        if (lock.hasQueuedThread(t)) {
            throw new RuntimeException("finished thread still reported as queued");
        }
        if (lock.hasQueuedThread(current)) {
            throw new RuntimeException("current thread reported as queued after release");
        }
    }
}
