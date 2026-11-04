/*
 * @test
 * @summary Verifies that AbstractQueuedSynchronizer.releaseShared returns the result of tryReleaseShared and forwards the argument
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static final class Sync extends AbstractQueuedSynchronizer {
        private final boolean result;
        int calls;
        int lastArg;

        Sync(boolean result) {
            this.result = result;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
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

    public static void main(String[] args) {
        Sync releasing = new Sync(true);
        check(releasing.releaseShared(3), "releaseShared must return true when tryReleaseShared returns true");
        check(releasing.calls == 1, "tryReleaseShared must be invoked exactly once");
        check(releasing.lastArg == 3, "releaseShared must forward its argument to tryReleaseShared");

        Sync notReleasing = new Sync(false);
        check(!notReleasing.releaseShared(5), "releaseShared must return false when tryReleaseShared returns false");
        check(notReleasing.calls == 1, "tryReleaseShared must be invoked exactly once on failure");
        check(notReleasing.lastArg == 5, "releaseShared must forward its argument on failure");

        Sync zeroArg = new Sync(true);
        check(zeroArg.releaseShared(0), "releaseShared must return true for a zero argument when released");
        check(zeroArg.lastArg == 0, "zero argument must be forwarded unchanged");

        Sync repeated = new Sync(true);
        for (int i = 0; i < 3; i++) {
            check(repeated.releaseShared(1), "repeated releaseShared must keep returning true");
        }
        check(repeated.calls == 3, "each releaseShared call must invoke tryReleaseShared once");
    }
}
