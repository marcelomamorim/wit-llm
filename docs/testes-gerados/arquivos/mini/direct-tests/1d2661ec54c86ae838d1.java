/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.signal() behavior
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static class Sync extends AbstractQueuedSynchronizer {
        // Override to control isHeldExclusively behavior
        private final boolean held;
        Sync(boolean held) { this.held = held; }
        @Override
        protected boolean isHeldExclusively() {
            return held;
        }
    }

    public static void main(String[] args) {
        testSignalThrowsIfNotHeldExclusively();
        testSignalNoWaitersDoesNotThrow();
    }

    private static void testSignalThrowsIfNotHeldExclusively() {
        Sync sync = new Sync(false); // isHeldExclusively returns false
        try {
            sync.signal();
            throw new Error("Expected IllegalMonitorStateException not thrown");
        } catch (IllegalMonitorStateException expected) {
            // expected
        }
    }

    private static void testSignalNoWaitersDoesNotThrow() {
        Sync sync = new Sync(true); // isHeldExclusively returns true
        // firstWaiter is null by default, so signal should not throw
        sync.signal();
    }
}