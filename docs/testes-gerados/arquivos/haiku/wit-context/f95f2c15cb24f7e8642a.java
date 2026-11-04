/*
 * @test
 * @summary Checks AbstractQueuedSynchronizer.release(int) delegation to tryRelease and its default UnsupportedOperationException
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static class ReleaseSync extends AbstractQueuedSynchronizer {
        private final boolean result;
        int calls;
        int lastArg = Integer.MIN_VALUE;

        ReleaseSync(boolean result) {
            this.result = result;
        }

        @Override
        protected boolean tryRelease(int arg) {
            calls++;
            lastArg = arg;
            return result;
        }
    }

    static class DefaultSync extends AbstractQueuedSynchronizer {
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ReleaseSync failing = new ReleaseSync(false);
        check(!failing.release(5), "release must return false when tryRelease returns false");
        check(failing.calls == 1, "tryRelease must be invoked exactly once");
        check(failing.lastArg == 5, "release must pass its argument to tryRelease");

        ReleaseSync succeeding = new ReleaseSync(true);
        check(succeeding.release(7), "release must return true when tryRelease returns true");
        check(succeeding.calls == 1, "tryRelease must be invoked exactly once on success");
        check(succeeding.lastArg == 7, "release must pass argument 7 to tryRelease");

        ReleaseSync zero = new ReleaseSync(true);
        check(zero.release(0), "release(0) must propagate true from tryRelease");
        check(zero.lastArg == 0, "release must pass argument 0 to tryRelease");

        DefaultSync defaults = new DefaultSync();
        boolean thrown = false;
        try {
            defaults.release(1);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "default tryRelease must cause UnsupportedOperationException from release");
    }
}
