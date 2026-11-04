/*
 * @test
 * @summary Test AbstractQueuedLongSynchronizer.releaseShared(long) behavior
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static class TestSync extends AbstractQueuedLongSynchronizer {
        private static final long serialVersionUID = 1L;

        // State is initially 0
        TestSync() {
            super();
        }

        // tryReleaseShared returns true if state is positive after release
        @Override
        protected boolean tryReleaseShared(long arg) {
            long s = getState();
            if (s == 0) {
                return false;
            }
            setState(s - arg);
            return getState() <= 0;
        }

        // tryAcquireShared returns 1 if state is positive, else -1
        @Override
        protected long tryAcquireShared(long arg) {
            return (getState() > 0) ? 1 : -1;
        }
    }

    public static void main(String[] args) {
        TestSync sync = new TestSync();

        // Initially state is 0, releaseShared should return false
        if (sync.releaseShared(1)) {
            throw new Error("releaseShared returned true when state was 0");
        }

        // Set state to 2
        sync.setState(2);

        // releaseShared with arg=1 should decrement state to 1 and return false (state still positive)
        boolean result1 = sync.releaseShared(1);
        if (result1) {
            throw new Error("releaseShared returned true when state still positive");
        }
        if (sync.getState() != 1) {
            throw new Error("State not decremented correctly, expected 1 but was " + sync.getState());
        }

        // releaseShared with arg=1 again should decrement state to 0 and return true
        boolean result2 = sync.releaseShared(1);
        if (!result2) {
            throw new Error("releaseShared returned false when state reached zero");
        }
        if (sync.getState() != 0) {
            throw new Error("State not decremented correctly to zero, was " + sync.getState());
        }

        // releaseShared with arg=1 again should return false (state is zero)
        boolean result3 = sync.releaseShared(1);
        if (result3) {
            throw new Error("releaseShared returned true when state was zero again");
        }

        System.out.println("PASS");
    }
}
