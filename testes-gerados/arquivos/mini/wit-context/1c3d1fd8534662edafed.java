/*
 * @test
 * @summary Test AbstractQueuedLongSynchronizer.signal() method behavior
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    // Minimal subclass to access protected constructor and isHeldExclusively method
    static class Sync extends AbstractQueuedLongSynchronizer {
        private static final long serialVersionUID = 1L;

        Sync() {
            super();
        }

        // Override to simulate ownership
        @Override
        protected boolean isHeldExclusively() {
            return held;
        }

        // Control ownership state for testing
        private volatile boolean held = false;

        void setHeld(boolean h) {
            held = h;
        }

        // Expose signal method for testing
        public void testSignal() {
            signal();
        }

        // Dummy fields to simulate ConditionObject's firstWaiter and doSignal
        volatile Node firstWaiter = null;

        // Dummy Node class to satisfy references
        static class Node {}

        // Dummy doSignal to simulate signaling
        void doSignal(Node first) {
            signaled = true;
        }

        volatile boolean signaled = false;
    }

    public static void main(String[] args) {
        Sync sync = new Sync();

        // Test 1: signal() without ownership should throw IllegalMonitorStateException
        try {
            sync.setHeld(false);
            sync.testSignal();
            throw new RuntimeException("Expected IllegalMonitorStateException not thrown");
        } catch (IllegalMonitorStateException expected) {
            // Expected exception
        }

        // Test 2: signal() with ownership and no waiters should not throw and not signal
        sync.setHeld(true);
        sync.firstWaiter = null;
        sync.signaled = false;
        sync.testSignal();
        if (sync.signaled) {
            throw new RuntimeException("Signal should not have been sent when no waiters");
        }

        // Test 3: signal() with ownership and a waiter should call doSignal
        sync.firstWaiter = new Sync.Node();
        sync.signaled = false;
        sync.testSignal();
        if (!sync.signaled) {
            throw new RuntimeException("Signal was not sent when waiter present");
        }

        System.out.println("PASS");
    }
}
