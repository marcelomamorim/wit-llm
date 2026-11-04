/*
 * @test
 * @summary Checks acquireShared fast path and default UnsupportedOperationException from tryAcquireShared
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static class Sync extends AbstractQueuedSynchronizer {
        private final int result;
        int calls;
        int lastArg = Integer.MIN_VALUE;

        Sync(int result) {
            this.result = result;
        }

        @Override
        protected int tryAcquireShared(int arg) {
            calls++;
            lastArg = arg;
            return result;
        }
    }

    static class Unsupported extends AbstractQueuedSynchronizer {
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Sync positive = new Sync(1);
        positive.acquireShared(5);
        check(positive.calls == 1, "tryAcquireShared should be invoked once");
        check(positive.lastArg == 5, "tryAcquireShared should receive the argument");

        Sync zero = new Sync(0);
        zero.acquireShared(0);
        check(zero.calls == 1, "non-negative result should not block or retry");
        check(zero.lastArg == 0, "argument 0 should be forwarded");

        Unsupported unsupported = new Unsupported();
        boolean thrown = false;
        try {
            unsupported.acquireShared(1);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "default tryAcquireShared should cause UnsupportedOperationException");
    }
}
