/*
 * @test
 * @summary Witup-derived checks for ReentrantLock.hasQueuedThread null handling and queued state
 * @run main ReentrantLockWitupTest
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {
    public static void main(String[] args) throws Exception {
        ReentrantLock lock = new ReentrantLock();

        boolean npeThrown = false;
        try {
            lock.hasQueuedThread(null);
        } catch (NullPointerException expected) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("expected NullPointerException for null thread");
        }

        if (lock.hasQueuedThread(Thread.currentThread())) {
            throw new AssertionError("current thread must not be queued when it does not wait");
        }

        lock.lock();
        Thread waiter = new Thread(() -> {
            lock.lock();
            lock.unlock();
        });
        waiter.start();

        long deadline = System.nanoTime() + 10_000_000_000L;
        while (!lock.hasQueuedThread(waiter)) {
            if (System.nanoTime() > deadline) {
                lock.unlock();
                throw new AssertionError("waiter thread never reported as queued");
            }
            Thread.sleep(10);
        }

        lock.unlock();
        waiter.join(10_000);

        if (waiter.isAlive()) {
            throw new AssertionError("waiter thread did not terminate");
        }
        if (lock.hasQueuedThread(waiter)) {
            throw new AssertionError("terminated thread must not be queued");
        }
    }
}
