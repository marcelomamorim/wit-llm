/*
 * @test
 * @summary Basic behavior of AbstractQueuedSynchronizer.acquire(int)
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static final class Sync extends AbstractQueuedSynchronizer {
        private final boolean[] script;
        int tryCalls;
        int lastArg;

        Sync(boolean... script) {
            this.script = script;
            this.lastArg = Integer.MIN_VALUE;
        }

        @Override
        protected boolean tryAcquire(int arg) {
            lastArg = arg;
            int i = tryCalls++;
            if (i >= script.length) {
                throw new IllegalStateException("unexpected tryAcquire call " + i);
            }
            return script[i];
        }
    }

    static final class Failing extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            throw new IllegalStateException("boom");
        }
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new AssertionError(msg);
        }
    }

    public static void main(String[] args) {
        Sync immediate = new Sync(true);
        immediate.acquire(7);
        check(immediate.tryCalls == 1, "tryAcquire should be called once");
        check(immediate.lastArg == 7, "tryAcquire should receive arg 7");
        check(!Thread.currentThread().isInterrupted(), "no interrupt expected");

        Sync retry = new Sync(false, true);
        retry.acquire(3);
        check(retry.tryCalls == 2, "tryAcquire should be retried once after failure");
        check(retry.lastArg == 3, "tryAcquire should receive arg 3");
        check(!Thread.currentThread().isInterrupted(), "no interrupt expected after queued acquire");

        Failing failing = new Failing();
        boolean thrown = false;
        try {
            failing.acquire(1);
        } catch (IllegalStateException e) {
            thrown = "boom".equals(e.getMessage());
        }
        check(thrown, "exception from tryAcquire should propagate out of acquire");
    }
}
