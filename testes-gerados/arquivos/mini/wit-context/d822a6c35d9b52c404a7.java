/*
 * @test
 * @summary Test AbstractQueuedLongSynchronizer.acquireShared(long) behavior
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static class TestAQLS extends AbstractQueuedLongSynchronizer {
        private static final long serialVersionUID = 1L;

        @Override
        protected long tryAcquireShared(long arg) {
            // Return negative to force doAcquireShared call
            return -1L;
        }

        // Expose doAcquireShared for testing by overriding to track invocation
        boolean doAcquireSharedCalled = false;

        @Override
        protected void doAcquireShared(long arg) {
            doAcquireSharedCalled = true;
            // No actual synchronization logic needed for this test
        }
    }

    public static void main(String[] args) {
        TestAQLS sync = new TestAQLS();

        // Test case: tryAcquireShared returns negative, doAcquireShared should be called
        sync.doAcquireSharedCalled = false;
        sync.acquireShared(1L);
        if (!sync.doAcquireSharedCalled) {
            throw new AssertionError("doAcquireShared was not called when tryAcquireShared returned negative");
        }

        // Test case: tryAcquireShared returns non-negative, doAcquireShared should NOT be called
        TestAQLS sync2 = new TestAQLS() {
            @Override
            protected long tryAcquireShared(long arg) {
                return 0L; // non-negative
            }

            @Override
            protected void doAcquireShared(long arg) {
                throw new AssertionError("doAcquireShared should not be called when tryAcquireShared returns non-negative");
            }
        };

        sync2.acquireShared(1L);

        System.out.println("AbstractQueuedLongSynchronizer.acquireShared(long) tests passed.");
    }
}
