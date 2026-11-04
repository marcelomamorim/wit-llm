/*
 * @test
 * @summary Verifies isQueued(Thread) null handling and queued/non-queued observable behavior
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static class Sync extends AbstractQueuedLongSynchronizer {
        @Override
        protected boolean tryAcquire(long arg) {
            return false;
        }

        @Override
        protected boolean tryRelease(long arg) {
            return true;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Sync sync = new Sync();

        boolean npeThrown = false;
        try {
            sync.isQueued(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "isQueued(null) must throw NullPointerException");

        check(!sync.isQueued(Thread.currentThread()),
              "current thread must not be queued");

        Thread t = new Thread(() -> {
            try {
                sync.acquireInterruptibly(1);
            } catch (InterruptedException e) {
                // expected on interrupt
            }
        });

        check(!sync.isQueued(t), "unstarted thread must not be queued");

        t.start();
        long deadline = System.nanoTime() + 10_000_000_000L;
        while (!sync.isQueued(t)) {
            if (System.nanoTime() > deadline) {
                throw new AssertionError("thread was not observed as queued");
            }
            Thread.sleep(10);
        }
        check(sync.isQueued(t), "blocked thread must be queued");

        t.interrupt();
        t.join(10_000);
        check(!t.isAlive(), "thread must terminate after interrupt");
        check(!sync.isQueued(t), "terminated thread must not be queued");
    }
}
