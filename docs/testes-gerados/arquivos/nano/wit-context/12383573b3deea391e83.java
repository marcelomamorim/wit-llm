/*
 * @test
 * @summary Test for acquireShared method in AbstractQueuedSynchronizer
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    public static void main(String[] args) {
        testAcquireSharedWithNegativeArg();
        testAcquireSharedWithZeroArg();
        testAcquireSharedWithPositiveArg();
    }

    private static void testAcquireSharedWithNegativeArg() {
        AbstractQueuedSynchronizer aqs = new DummyAQS();
        try {
            aqs.acquireShared(-1);
            System.out.println("Test failed: No exception for negative arg");
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: UnsupportedOperationException for negative arg");
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception for negative arg: " + e);
        }
    }

    private static void testAcquireSharedWithZeroArg() {
        AbstractQueuedSynchronizer aqs = new DummyAQS();
        try {
            aqs.acquireShared(0);
            System.out.println("Passed: acquireShared with zero arg");
        } catch (Exception e) {
            System.out.println("Test failed: Exception for zero arg: " + e);
        }
    }

    private static void testAcquireSharedWithPositiveArg() {
        AbstractQueuedSynchronizer aqs = new DummyAQS();
        try {
            aqs.acquireShared(1);
            System.out.println("Passed: acquireShared with positive arg");
        } catch (Exception e) {
            System.out.println("Test failed: Exception for positive arg: " + e);
        }
    }

    static class DummyAQS extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int arg) {
            if (arg < 0) {
                throw new UnsupportedOperationException();
            }
            return 1;
        }
    }
}
