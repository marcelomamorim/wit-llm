/*
 * @test
 * @summary Test for acquire method in AbstractQueuedLongSynchronizer
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    public static void main(String[] args) {
        testAcquireWithSuccessfulTryAcquire();
        testAcquireWithFailedTryAcquire();
    }

    private static void testAcquireWithSuccessfulTryAcquire() {
        AbstractQueuedLongSynchronizer synchronizer = new AbstractQueuedLongSynchronizer() {
            @Override
            protected boolean tryAcquire(long arg) {
                return true;
            }
        };
        try {
            synchronizer.acquire(1L);
            System.out.println("testAcquireWithSuccessfulTryAcquire passed");
        } catch (Exception e) {
            System.err.println("testAcquireWithSuccessfulTryAcquire failed: " + e);
        }
    }

    private static void testAcquireWithFailedTryAcquire() {
        AbstractQueuedLongSynchronizer synchronizer = new AbstractQueuedLongSynchronizer() {
            @Override
            protected boolean tryAcquire(long arg) {
                return false;
            }
        };
        try {
            synchronizer.acquire(1L);
            System.out.println("testAcquireWithFailedTryAcquire passed");
        } catch (UnsupportedOperationException e) {
            System.err.println("testAcquireWithFailedTryAcquire failed: " + e);
        } catch (Exception e) {
            System.err.println("testAcquireWithFailedTryAcquire unexpected exception: " + e);
        }
    }
}
