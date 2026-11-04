/*
 * @test
 * @summary Verifies AbstractQueuedLongSynchronizer.acquireShared returns without blocking when tryAcquireShared returns a non-negative value
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static final class Sync extends AbstractQueuedLongSynchronizer {
        private final long result;
        int calls;
        long lastArg = Long.MIN_VALUE;

        Sync(long result) {
            this.result = result;
        }

        @Override
        protected long tryAcquireShared(long arg) {
            calls++;
            lastArg = arg;
            return result;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPositiveResultReturnsAndPassesArg() {
        Sync s = new Sync(1L);
        s.acquireShared(42L);
        check(s.calls == 1, "tryAcquireShared should be called once, was " + s.calls);
        check(s.lastArg == 42L, "argument not propagated: " + s.lastArg);
    }

    static void testZeroResultReturnsWithoutBlocking() {
        Sync s = new Sync(0L);
        s.acquireShared(7L);
        check(s.calls == 1, "tryAcquireShared should be called once, was " + s.calls);
        check(s.lastArg == 7L, "argument not propagated: " + s.lastArg);
    }

    static void testLargeArgumentIsPassedThrough() {
        Sync s = new Sync(5L);
        s.acquireShared(Long.MAX_VALUE);
        check(s.calls == 1, "tryAcquireShared should be called once, was " + s.calls);
        check(s.lastArg == Long.MAX_VALUE, "large argument not propagated: " + s.lastArg);
    }

    public static void main(String[] args) {
        testPositiveResultReturnsAndPassesArg();
        testZeroResultReturnsWithoutBlocking();
        testLargeArgumentIsPassedThrough();
    }
}
