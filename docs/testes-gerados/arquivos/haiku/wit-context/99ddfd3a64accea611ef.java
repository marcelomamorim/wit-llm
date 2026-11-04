/*
 * @test
 * @summary Tests public observable behavior of AbstractQueuedSynchronizer.acquire(int)
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static final class PlainSync extends AbstractQueuedSynchronizer {
    }

    static final class RecordingSync extends AbstractQueuedSynchronizer {
        int calls;
        int lastArg = Integer.MIN_VALUE;

        @Override
        protected boolean tryAcquire(int arg) {
            calls++;
            lastArg = arg;
            return true;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDefaultTryAcquireThrowsUnsupported() {
        boolean thrown = false;
        try {
            new PlainSync().acquire(1);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "acquire must propagate UnsupportedOperationException from default tryAcquire");
    }

    static void testSuccessfulTryAcquireIsInvokedWithArgument() {
        RecordingSync sync = new RecordingSync();
        sync.acquire(7);
        check(sync.calls == 1, "tryAcquire should be invoked exactly once, was " + sync.calls);
        check(sync.lastArg == 7, "tryAcquire should receive arg 7, was " + sync.lastArg);
    }

    static void testInterruptStatusPreservedWhenAcquireSucceeds() {
        Thread.interrupted();
        Thread.currentThread().interrupt();
        try {
            RecordingSync sync = new RecordingSync();
            sync.acquire(3);
            boolean flag = Thread.currentThread().isInterrupted();
            check(flag, "interrupt status should remain set after immediate acquire");
        } finally {
            Thread.interrupted();
        }
    }

    public static void main(String[] args) {
        testDefaultTryAcquireThrowsUnsupported();
        testSuccessfulTryAcquireIsInvokedWithArgument();
        testInterruptStatusPreservedWhenAcquireSucceeds();
    }
}
