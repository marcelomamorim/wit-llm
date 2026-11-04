/*
 * @test
 * @summary Tests acquire(long) of AbstractQueuedLongSynchronizer through a subclass that controls tryAcquire(long)
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static class Sync extends AbstractQueuedLongSynchronizer {
        private final int succeedOn;
        int calls;
        long lastArg;

        Sync(int succeedOn) {
            this.succeedOn = succeedOn;
        }

        @Override
        protected boolean tryAcquire(long arg) {
            calls++;
            lastArg = arg;
            return calls >= succeedOn;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }

    public static void main(String[] args) {
        // Immediate success: no queueing, long argument passed unchanged
        Sync s1 = new Sync(1);
        long big = 1L << 40;
        s1.acquire(big);
        check(s1.calls == 1, "tryAcquire should be called once, was " + s1.calls);
        check(s1.lastArg == big, "arg not passed intact: " + s1.lastArg);
        check(!s1.hasQueuedThreads(), "no thread should be queued after immediate acquire");

        // Immediate success must not touch the interrupt status
        Thread.currentThread().interrupt();
        Sync s2 = new Sync(1);
        s2.acquire(5L);
        check(Thread.currentThread().isInterrupted(),
              "interrupt status should be preserved when tryAcquire succeeds");
        Thread.interrupted();

        // First attempt fails, second succeeds on the queued path without parking
        Sync s3 = new Sync(2);
        s3.acquire(7L);
        check(s3.calls == 2, "tryAcquire should be called twice, was " + s3.calls);
        check(s3.lastArg == 7L, "queued path arg mismatch: " + s3.lastArg);
        check(!s3.hasQueuedThreads(), "queue should be empty after acquisition");
    }
}
