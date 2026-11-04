/*
 * @test
 * @summary Checks isQueued(Thread) null handling and queued/dequeued observable behavior of AbstractQueuedSynchronizer
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static class BlockingSync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        AbstractQueuedSynchronizer fresh = new AbstractQueuedSynchronizer() { };

        boolean npeThrown = false;
        try {
            fresh.isQueued(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "isQueued(null) must throw NullPointerException");

        check(!fresh.isQueued(Thread.currentThread()),
              "current thread must not be queued on a fresh synchronizer");

        final BlockingSync sync = new BlockingSync();
        final Thread waiter = new Thread(() -> {
            try {
                sync.acquireInterruptibly(1);
            } catch (InterruptedException e) {
                // expected: interrupted while queued
            }
        });

        check(!sync.isQueued(waiter), "thread must not be queued before starting");

        waiter.start();
        long deadline = System.currentTimeMillis() + 10_000L;
        while (!sync.isQueued(waiter) && System.currentTimeMillis() < deadline) {
            Thread.yield();
        }
        check(sync.isQueued(waiter), "waiting thread must become queued");

        waiter.interrupt();
        waiter.join(10_000L);
        check(!waiter.isAlive(), "waiter thread must terminate after interruption");
        check(!sync.isQueued(waiter), "interrupted thread must be dequeued");
    }
}
