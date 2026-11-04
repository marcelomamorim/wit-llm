/* @test    @summary Test acquireShared method of AbstractQueuedSynchronizer for non-negative argument */
/* @run main AbstractQueuedSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    public static void main(String[] args) {
        testAcquireSharedWithZero();
        testAcquireSharedWithPositive();
    }

    private static void testAcquireSharedWithZero() {
        AbstractQueuedSynchronizer aqs = new DummyAQS();
        try {
            aqs.acquireShared(0);
            System.out.println("PASS: acquireShared with 0 argument completed without exception.");
        } catch (Exception e) {
            System.out.println("FAIL: acquireShared with 0 argument threw exception: " + e);
        }
    }

    private static void testAcquireSharedWithPositive() {
        AbstractQueuedSynchronizer aqs = new DummyAQS();
        try {
            aqs.acquireShared(1);
            System.out.println("PASS: acquireShared with positive argument completed without exception.");
        } catch (Exception e) {
            System.out.println("FAIL: acquireShared with positive argument threw exception: " + e);
        }
    }

    static class DummyAQS extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int arg) {
            return 1; // simulate success
        }
    }
}
