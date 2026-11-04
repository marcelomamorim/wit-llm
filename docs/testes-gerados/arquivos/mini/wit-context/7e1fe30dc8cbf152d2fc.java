/*
 * @test
 * @summary Test AbstractQueuedLongSynchronizer.releaseShared(long) behavior
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static class TestAQLS extends AbstractQueuedLongSynchronizer {
        private static final long serialVersionUID = 1L;

        // Override tryReleaseShared to simulate release behavior
        @Override
        protected boolean tryReleaseShared(long arg) {
            // For test: release only if arg is positive
            return arg > 0;
        }

        // Expose doReleaseShared for testing (no-op override)
        @Override
        protected void doReleaseShared() {
            // no-op for test
        }
    }

    public static void main(String[] args) {
        TestAQLS sync = new TestAQLS();

        // Test releaseShared returns true when tryReleaseShared returns true
        boolean released = sync.releaseShared(1L);
        if (!released) {
            throw new Error("releaseShared(1) should return true when tryReleaseShared returns true");
        }

        // Test releaseShared returns false when tryReleaseShared returns false
        released = sync.releaseShared(0L);
        if (released) {
            throw new Error("releaseShared(0) should return false when tryReleaseShared returns false");
        }

        // Test releaseShared returns false when tryReleaseShared returns false for negative arg
        released = sync.releaseShared(-1L);
        if (released) {
            throw new Error("releaseShared(-1) should return false when tryReleaseShared returns false");
        }

        System.out.println("All tests passed.");
    }
}
