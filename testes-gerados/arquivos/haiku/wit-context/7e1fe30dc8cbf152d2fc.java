/*
 * @test
 * @summary Checks releaseShared delegation to tryReleaseShared, its boolean result, and the default UnsupportedOperationException
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static final class ReleaseSync extends AbstractQueuedLongSynchronizer {
        private final boolean result;
        long lastArg = Long.MIN_VALUE;
        int calls;

        ReleaseSync(boolean result) {
            this.result = result;
        }

        @Override
        protected boolean tryReleaseShared(long arg) {
            lastArg = arg;
            calls++;
            return result;
        }
    }

    static final class DefaultSync extends AbstractQueuedLongSynchronizer {
    }

    public static void main(String[] args) {
        ReleaseSync released = new ReleaseSync(true);
        if (!released.releaseShared(42L)) {
            throw new RuntimeException("releaseShared should return true when tryReleaseShared returns true");
        }
        if (released.calls != 1 || released.lastArg != 42L) {
            throw new RuntimeException("tryReleaseShared should receive the argument exactly once");
        }

        ReleaseSync notReleased = new ReleaseSync(false);
        if (notReleased.releaseShared(7L)) {
            throw new RuntimeException("releaseShared should return false when tryReleaseShared returns false");
        }
        if (notReleased.calls != 1 || notReleased.lastArg != 7L) {
            throw new RuntimeException("tryReleaseShared should be invoked for the false path");
        }

        DefaultSync defaults = new DefaultSync();
        try {
            defaults.releaseShared(1L);
            throw new RuntimeException("expected UnsupportedOperationException from default tryReleaseShared");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
