/*
 * @test
 * @summary Tests releaseShared delegation to tryReleaseShared and its default exception behavior
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static class ConfigurableSync extends AbstractQueuedSynchronizer {
        private final boolean result;

        ConfigurableSync(boolean result) {
            this.result = result;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
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
        boolean thrown = false;
        try {
            new DefaultSync().releaseShared(1);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "releaseShared should propagate UnsupportedOperationException when tryReleaseShared is not overridden");

        check(new ConfigurableSync(false).releaseShared(1) == false,
              "releaseShared should return false when tryReleaseShared returns false");

        check(new ConfigurableSync(true).releaseShared(1) == true,
              "releaseShared should return true when tryReleaseShared returns true");
    }
}
