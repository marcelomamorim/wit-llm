/*
 * @test
 * @summary Witup-style checks for AbstractQueuedLongSynchronizer.acquireShared
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static final class Sync extends AbstractQueuedLongSynchronizer {
        private final long ret;
        long lastArg = Long.MIN_VALUE;
        int calls;

        Sync(long ret) {
            this.ret = ret;
        }

        @Override
        protected long tryAcquireShared(long arg) {
            lastArg = arg;
            calls++;
            return ret;
        }
    }

    public static void main(String[] args) {
        testPassesArgumentAndReturnsWhenAcquired();
        testNonNegativeResultDoesNotBlock();
        testDefaultTryAcquireSharedThrowsUnsupported();
    }

    static void testPassesArgumentAndReturnsWhenAcquired() {
        Sync s = new Sync(1L);
        s.acquireShared(42L);
        if (s.calls != 1) {
            throw new RuntimeException("tryAcquireShared calls expected 1 but was " + s.calls);
        }
        if (s.lastArg != 42L) {
            throw new RuntimeException("argument expected 42 but was " + s.lastArg);
        }
    }

    static void testNonNegativeResultDoesNotBlock() {
        Sync s = new Sync(0L);
        s.acquireShared(-7L);
        if (s.calls != 1) {
            throw new RuntimeException("tryAcquireShared calls expected 1 but was " + s.calls);
        }
        if (s.lastArg != -7L) {
            throw new RuntimeException("argument expected -7 but was " + s.lastArg);
        }
    }

    static void testDefaultTryAcquireSharedThrowsUnsupported() {
        AbstractQueuedLongSynchronizer plain = new AbstractQueuedLongSynchronizer() { };
        try {
            plain.acquireShared(1L);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("UnsupportedOperationException expected from default tryAcquireShared");
    }
}
