/* @test    @summary Test acquireShared method of AbstractQueuedLongSynchronizer for non-negative argument.    @run main AbstractQueuedLongSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    public static void main(String[] args) {
        testAcquireSharedWithZero();
        testAcquireSharedWithPositive();
    }

    static class DummySync extends AbstractQueuedLongSynchronizer {
        @Override
        protected int tryAcquireShared(long arg) {
            if (arg >= 0) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    static void testAcquireSharedWithZero() {
        DummySync sync = new DummySync();
        try {
            sync.acquireShared(0L);
            System.out.println("testAcquireSharedWithZero passed");
        } catch (Exception e) {
            System.out.println("testAcquireSharedWithZero failed: " + e);
        }
    }

    static void testAcquireSharedWithPositive() {
        DummySync sync = new DummySync();
        try {
            sync.acquireShared(10L);
            System.out.println("testAcquireSharedWithPositive passed");
        } catch (Exception e) {
            System.out.println("testAcquireSharedWithPositive failed: " + e);
        }
    }
}
