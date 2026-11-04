/*
 * @test
 * @summary Verifies the observable behavior of AbstractQueuedSynchronizer.release(int) through a public subclass that overrides tryRelease.
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static class Sync extends AbstractQueuedSynchronizer {
        private final boolean result;
        int calls = 0;
        int lastArg = Integer.MIN_VALUE;
        RuntimeException toThrow = null;

        Sync(boolean result) {
            super();
            this.result = result;
        }

        @Override
        protected boolean tryRelease(int arg) {
            calls++;
            lastArg = arg;
            if (toThrow != null) {
                throw toThrow;
            }
            return result;
        }
    }

    static void testReleaseReturnsFalseWhenTryReleaseFails() {
        Sync s = new Sync(false);
        boolean r = s.release(7);
        check(!r, "release must return false when tryRelease returns false");
        check(s.calls == 1, "tryRelease must be invoked exactly once");
        check(s.lastArg == 7, "tryRelease must receive the same argument");
    }

    static void testReleaseReturnsTrueWhenTryReleaseSucceeds() {
        Sync s = new Sync(true);
        boolean r = s.release(3);
        check(r, "release must return true when tryRelease returns true");
        check(s.calls == 1, "tryRelease must be invoked exactly once on success");
        check(s.lastArg == 3, "tryRelease must receive the same argument on success");
    }

    static void testReleaseWithNegativeArgument() {
        Sync s = new Sync(true);
        boolean r = s.release(-5);
        check(r, "release must return true for negative argument when tryRelease succeeds");
        check(s.lastArg == -5, "negative argument must be forwarded unchanged");
    }

    static void testReleasePropagatesTryReleaseException() {
        Sync s = new Sync(true);
        s.toThrow = new IllegalStateException("boom");
        boolean thrown = false;
        try {
            s.release(1);
        } catch (IllegalStateException e) {
            thrown = "boom".equals(e.getMessage());
        }
        check(thrown, "exception thrown by tryRelease must propagate out of release");
        check(s.calls == 1, "tryRelease must be invoked before the exception propagates");
    }

    public static void main(String[] args) {
        testReleaseReturnsFalseWhenTryReleaseFails();
        testReleaseReturnsTrueWhenTryReleaseSucceeds();
        testReleaseWithNegativeArgument();
        testReleasePropagatesTryReleaseException();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
