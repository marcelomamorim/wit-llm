/* @test    @summary Test for AbstractQueuedSynchronizer.signalAll() method behavior    @run main AbstractQueuedSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    public static void main(String[] args) {
        testSignalAllWithoutLock();
        testSignalAllWithLock();
    }

    private static void testSignalAllWithoutLock() {
        AbstractQueuedSynchronizer aqs = new DummyAQS();
        boolean exceptionThrown = false;
        try {
            aqs.signalAll();
        } catch (IllegalMonitorStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalMonitorStateException was not thrown.");
        }
    }

    private static void testSignalAllWithLock() {
        DummyAQS aqs = new DummyAQS();
        aqs.setLockHeld(true);
        try {
            aqs.signalAll();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static class DummyAQS extends AbstractQueuedSynchronizer {
        private boolean lockHeld = false;

        public void setLockHeld(boolean held) {
            this.lockHeld = held;
        }

        @Override
        protected boolean isHeldExclusively() {
            return lockHeld;
        }

        @Override
        protected void doSignalAll(Node first) {
            // No-op for testing
        }
    }
}
