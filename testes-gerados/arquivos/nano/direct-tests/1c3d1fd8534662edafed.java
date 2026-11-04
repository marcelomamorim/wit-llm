/* @test    @summary Test signal() method of AbstractQueuedLongSynchronizer for correct behavior    @run main AbstractQueuedLongSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    public static void main(String[] args) {
        testSignalWithoutLock();
        testSignalWithLock();
    }

    private static void testSignalWithoutLock() {
        AbstractQueuedLongSynchronizer synchronizer = new DummySynchronizer();
        boolean exceptionThrown = false;
        try {
            synchronizer.signal();
        } catch (IllegalMonitorStateException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "signal() should throw IllegalMonitorStateException when not held";
    }

    private static void testSignalWithLock() {
        DummySynchronizer synchronizer = new DummySynchronizer();
        synchronizer.acquire();
        boolean exceptionThrown = false;
        try {
            synchronizer.signal();
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            synchronizer.release();
        }
        assert !exceptionThrown : "signal() should not throw when lock is held";
    }

    static class DummySynchronizer extends AbstractQueuedLongSynchronizer {
        @Override
        protected boolean isHeldExclusively() {
            return true;
        }
    }
}
