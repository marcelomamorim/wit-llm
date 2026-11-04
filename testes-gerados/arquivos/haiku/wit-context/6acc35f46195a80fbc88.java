/*
 * @test
 * @summary WITUP-style tests for AbstractQueuedLongSynchronizer.isQueued(Thread)
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static class Gate extends AbstractQueuedLongSynchronizer {
        private volatile boolean open;

        @Override
        protected long tryAcquireShared(long acquires) {
            return open ? 1L : -1L;
        }

        @Override
        protected boolean tryReleaseShared(long releases) {
            open = true;
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        Gate gate = new Gate();

        boolean npeThrown = false;
        try {
            gate.isQueued(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "isQueued(null) must throw NullPointerException");

        check(!gate.isQueued(Thread.currentThread()),
              "current thread must not be queued initially");

        Thread waiter = new Thread(() -> gate.acquireShared(1L));
        check(!gate.isQueued(waiter), "unstarted thread must not be queued");

        waiter.start();
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(30);
        while (!gate.isQueued(waiter)) {
            if (System.nanoTime() > deadline) {
                throw new RuntimeException("waiter thread was never queued");
            }
            Thread.sleep(5);
        }
        check(gate.isQueued(waiter), "waiting thread must be reported as queued");
        check(!gate.isQueued(Thread.currentThread()),
              "current thread must still not be queued");

        gate.releaseShared(1L);
        waiter.join(TimeUnit.SECONDS.toMillis(30));
        check(!waiter.isAlive(), "waiter thread did not acquire after release");
        check(!gate.isQueued(waiter), "acquired thread must no longer be queued");
    }
}
