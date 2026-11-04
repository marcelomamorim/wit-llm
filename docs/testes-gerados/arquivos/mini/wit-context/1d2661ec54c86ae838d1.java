/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.signal() behavior including IllegalMonitorStateException
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    // Minimal subclass to instantiate AbstractQueuedSynchronizer
    static class Sync extends AbstractQueuedSynchronizer {
        Sync() {
            super();
        }

        // Override isHeldExclusively to control behavior for testing
        private boolean held;

        void setHeld(boolean held) {
            this.held = held;
        }

        @Override
        protected boolean isHeldExclusively() {
            return held;
        }
    }

    public static void main(String[] args) {
        Sync sync = new Sync();

        // Test that signal throws IllegalMonitorStateException if not held exclusively
        sync.setHeld(false);
        boolean threw = false;
        try {
            sync.signal();
        } catch (IllegalMonitorStateException ex) {
            threw = true;
        }
        if (!threw) {
            throw new Error("Expected IllegalMonitorStateException when signal() called without exclusive hold");
        }

        // Test that signal does not throw when held exclusively
        sync.setHeld(true);
        // firstWaiter is null by default, so doSignal is not called, but no exception expected
        try {
            sync.signal();
        } catch (Throwable t) {
            throw new Error("signal() threw unexpected exception when held exclusively", t);
        }

        System.out.println("AbstractQueuedSynchronizer.signal() tests passed");
    }
}
