/* @test    @summary Test for acquireShared method in AbstractQueuedLongSynchronizer
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    public static void main(String[] args) {
        testAcquireSharedWithNegativeResult();
        testAcquireSharedWithNonNegativeResult();
    }

    private static void testAcquireSharedWithNegativeResult() {
        AbstractQueuedLongSynchronizer synchronizer = new DummyAQLS();
        try {
            synchronizer.acquireShared(1L);
            System.out.println("Test failed: no exception thrown for negative tryAcquireShared result.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.out.println("Test failed: Caught unexpected exception: " + t);
        }
    }

    private static void testAcquireSharedWithNonNegativeResult() {
        AbstractQueuedLongSynchronizer synchronizer = new DummyAQLS();
        try {
            synchronizer.acquireShared(0L);
            System.out.println("Test passed: acquireShared completed without exception.");
        } catch (Throwable t) {
            System.out.println("Test failed: Caught unexpected exception: " + t);
        }
    }

    private static class DummyAQLS extends AbstractQueuedLongSynchronizer {
        @Override
        protected int tryAcquireShared(long arg) {
            // Simulate a scenario where the shared lock is not acquired
            return -1;
        }
    }
}
