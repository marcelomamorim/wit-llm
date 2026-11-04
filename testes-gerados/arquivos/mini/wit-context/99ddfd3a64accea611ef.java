/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.acquire(int) behavior
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    // Minimal subclass to implement tryAcquire for testing
    static class TestAQS extends AbstractQueuedSynchronizer {
        private final boolean tryAcquireResult;

        TestAQS(boolean tryAcquireResult) {
            this.tryAcquireResult = tryAcquireResult;
        }

        @Override
        protected boolean tryAcquire(int arg) {
            return tryAcquireResult;
        }
    }

    public static void main(String[] args) {
        testAcquireSuccess();
        testAcquireQueuedInterrupt();
    }

    // Test case where tryAcquire returns true, so acquire returns immediately
    private static void testAcquireSuccess() {
        TestAQS aqs = new TestAQS(true);
        // Should not throw and not interrupt current thread
        aqs.acquire(1);
        // No direct observable state change, test passes if no exception
    }

    // Test case where tryAcquire returns false and acquireQueued returns true, causing selfInterrupt
    private static void testAcquireQueuedInterrupt() {
        TestAQS aqs = new TestAQS(false) {
            @Override
            protected boolean acquireQueued(Node node, int arg) {
                // Simulate that thread was interrupted while waiting
                return true;
            }

            @Override
            protected void selfInterrupt() {
                // Mark that selfInterrupt was called by setting a flag
                selfInterruptCalled = true;
            }

            boolean selfInterruptCalled = false;
        };

        // Add a waiter node to simulate queueing
        aqs.acquire(1);

        if (!((TestAQS) aqs).selfInterruptCalled) {
            throw new AssertionError("selfInterrupt was not called when acquireQueued returned true");
        }
    }

    // Minimal Node class to satisfy addWaiter argument
    static class Node {
        static final Node EXCLUSIVE = new Node();
    }

}