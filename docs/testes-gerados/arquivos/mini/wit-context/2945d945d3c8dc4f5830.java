/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.signalAll() behavior including IllegalMonitorStateException
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    // Minimal subclass to allow instantiation
    static class Sync extends AbstractQueuedSynchronizer {
        Sync() {
            super();
        }

        // Override to simulate ownership for testing
        @Override
        protected boolean isHeldExclusively() {
            return held;
        }

        // Control ownership state
        private boolean held = false;
        void setHeld(boolean h) {
            held = h;
        }

        // Expose signalAll for testing
        public void testSignalAll() {
            signalAll();
        }
    }

    public static void main(String[] args) {
        Sync sync = new Sync();

        // Test 1: signalAll() without ownership should throw IllegalMonitorStateException
        try {
            sync.setHeld(false);
            sync.testSignalAll();
            throw new RuntimeException("Expected IllegalMonitorStateException not thrown");
        } catch (IllegalMonitorStateException expected) {
            // Expected exception
        }

        // Test 2: signalAll() with ownership and no waiters should not throw
        sync.setHeld(true);
        try {
            sync.testSignalAll();
        } catch (Throwable t) {
            throw new RuntimeException("signalAll() threw unexpected exception when held exclusively and no waiters", t);
        }

        // Note: cannot test doSignalAll behavior or with waiters without complex setup
        System.out.println("AbstractQueuedSynchronizer.signalAll() tests passed");
    }
}
