/* @test    @summary Test acquire method of AbstractQueuedSynchronizer for successful acquisition and interruption handling    @run main AbstractQueuedSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static class DummySync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 1L;
        private final int stateToSet;

        DummySync(int initialState) {
            this.stateToSet = initialState;
        }

        @Override
        protected boolean tryAcquire(int arg) {
            // Simulate successful acquire if state is zero
            if (getState() == 0) {
                setState(stateToSet);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        testAcquireSuccess();
        testAcquireInterrupted();
    }

    static void testAcquireSuccess() {
        DummySync sync = new DummySync(0);
        try {
            sync.acquire(1);
            assert sync.getState() == sync.stateToSet : "State should be set after acquire";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testAcquireSuccess", e);
        }
    }

    static void testAcquireInterrupted() {
        DummySync sync = new DummySync(0);
        Thread t = new Thread(() -> {
            try {
                sync.acquire(1);
            } catch (InterruptedException e) {
                // Expected if interrupted
            }
        });
        t.start();
        t.interrupt();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Since acquire does not throw InterruptedException directly, check if thread was interrupted
        assert t.isInterrupted() : "Thread should be interrupted";
    }
}
