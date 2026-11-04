/*
 * @test
 * @summary Witup-style tests for AbstractQueuedSynchronizer.acquireShared
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static class Shared extends AbstractQueuedSynchronizer {
        int calls;
        int lastArg;
        int result;
        RuntimeException toThrow;

        Shared(int result) {
            this.result = result;
        }

        @Override
        protected int tryAcquireShared(int arg) {
            calls++;
            lastArg = arg;
            if (toThrow != null) {
                throw toThrow;
            }
            return result;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testZeroResultReturnsWithoutBlocking() {
        Shared s = new Shared(0);
        s.acquireShared(5);
        check(s.calls == 1, "tryAcquireShared should be called once");
        check(s.lastArg == 5, "arg should be forwarded");
    }

    static void testPositiveResultReturnsWithoutBlocking() {
        Shared s = new Shared(1);
        s.acquireShared(7);
        check(s.calls == 1, "tryAcquireShared should be called once");
        check(s.lastArg == 7, "arg should be forwarded");
    }

    static void testExceptionFromTryAcquirePropagates() {
        Shared s = new Shared(0);
        s.toThrow = new IllegalStateException();
        boolean caught = false;
        try {
            s.acquireShared(1);
        } catch (IllegalStateException e) {
            caught = true;
        }
        check(caught, "exception from tryAcquireShared should propagate");
        check(s.calls == 1, "tryAcquireShared should be called once");
    }

    public static void main(String[] args) {
        testZeroResultReturnsWithoutBlocking();
        testPositiveResultReturnsWithoutBlocking();
        testExceptionFromTryAcquirePropagates();
    }
}
