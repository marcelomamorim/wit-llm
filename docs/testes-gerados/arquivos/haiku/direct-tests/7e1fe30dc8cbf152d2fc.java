/*
 * @test
 * @summary Verifies releaseShared return value and argument propagation to tryReleaseShared
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static final class Sync extends AbstractQueuedLongSynchronizer {
        boolean result;
        long lastArg = Long.MIN_VALUE;
        int calls;

        @Override
        protected boolean tryReleaseShared(long arg) {
            calls++;
            lastArg = arg;
            return result;
        }

        long currentState() {
            return getState();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Sync s = new Sync();

        s.result = false;
        check(!s.releaseShared(42L), "releaseShared must return false when tryReleaseShared returns false");
        check(s.calls == 1, "tryReleaseShared must be invoked exactly once");
        check(s.lastArg == 42L, "argument must be passed to tryReleaseShared");
        check(s.currentState() == 0L, "state must remain unchanged by releaseShared");

        s.result = true;
        check(s.releaseShared(-7L), "releaseShared must return true when tryReleaseShared returns true");
        check(s.calls == 2, "tryReleaseShared must be invoked on each call");
        check(s.lastArg == -7L, "negative argument must be propagated unchanged");

        check(s.releaseShared(Long.MAX_VALUE), "releaseShared must handle Long.MAX_VALUE");
        check(s.calls == 3, "call count must be 3");
        check(s.lastArg == Long.MAX_VALUE, "Long.MAX_VALUE must be propagated unchanged");
        check(s.currentState() == 0L, "state must still be unchanged after successful release");
    }
}
