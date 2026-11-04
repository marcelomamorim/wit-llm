/*
 * @test
 * @summary Test AbstractQueuedLongSynchronizer.signal() behavior
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer.Node;

public class AbstractQueuedLongSynchronizerWitupTest {

    static class TestAQLS extends AbstractQueuedLongSynchronizer {
        private Node firstWaiter;

        // Expose firstWaiter for testing
        void setFirstWaiter(Node node) {
            firstWaiter = node;
        }

        // Override to expose firstWaiter
        @Override
        protected final Node getFirstWaiter() {
            return firstWaiter;
        }

        // Provide isHeldExclusively implementation
        @Override
        protected boolean isHeldExclusively() {
            return held;
        }

        // Track exclusive ownership
        private boolean held = false;
        void setHeld(boolean h) {
            held = h;
        }

        // Expose doSignal for testing
        boolean doSignalCalled = false;
        Node doSignalNode = null;

        void doSignal(Node node) {
            doSignalCalled = true;
            doSignalNode = node;
        }

        // Override signal to call our doSignal
        @Override
        public final void signal() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignal(first);
        }
    }

    public static void main(String[] args) {
        TestAQLS sync = new TestAQLS();

        // Test 1: signal() throws IllegalMonitorStateException if not held exclusively
        try {
            sync.setHeld(false);
            sync.signal();
            throw new RuntimeException("Expected IllegalMonitorStateException not thrown");
        } catch (IllegalMonitorStateException expected) {
            // expected
        }

        // Test 2: signal() does nothing if firstWaiter is null
        sync.setHeld(true);
        sync.setFirstWaiter(null);
        sync.doSignalCalled = false;
        sync.signal();
        if (sync.doSignalCalled) {
            throw new RuntimeException("doSignal should not be called when firstWaiter is null");
        }

        // Test 3: signal() calls doSignal with firstWaiter if held exclusively and firstWaiter not null
        Node dummyNode = new Node();
        sync.setFirstWaiter(dummyNode);
        sync.doSignalCalled = false;
        sync.doSignalNode = null;
        sync.signal();
        if (!sync.doSignalCalled) {
            throw new RuntimeException("doSignal was not called when firstWaiter was set");
        }
        if (sync.doSignalNode != dummyNode) {
            throw new RuntimeException("doSignal called with wrong node");
        }

        System.out.println("PASS");
    }
}
