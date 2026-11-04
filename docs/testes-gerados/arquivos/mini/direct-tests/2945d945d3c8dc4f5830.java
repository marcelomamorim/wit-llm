/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.signalAll() behavior
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static class Sync extends AbstractQueuedSynchronizer {
        // Allow signalAll only if held exclusively
        @Override
        protected boolean isHeldExclusively() {
            return held;
        }

        private boolean held = false;

        void setHeld(boolean h) {
            held = h;
        }

        // Expose firstWaiter for testing via reflection is not allowed,
        // so we rely on signalAll behavior only.
    }

    public static void main(String[] args) {
        Sync sync = new Sync();

        // Test 1: signalAll throws IllegalMonitorStateException if not held exclusively
        sync.setHeld(false);
        try {
            sync.signalAll();
            throw new RuntimeException("Expected IllegalMonitorStateException not thrown");
        } catch (IllegalMonitorStateException expected) {
            // expected
        }

        // Test 2: signalAll does not throw if held exclusively, even if no waiters
        sync.setHeld(true);
        sync.signalAll(); // no exception expected

        // No direct way to test doSignalAll or firstWaiter without subclassing or reflection,
        // so test coverage limited to observable behavior.
    }
}
