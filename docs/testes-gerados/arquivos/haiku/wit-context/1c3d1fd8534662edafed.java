/*
 * @test
 * @summary Verifies that ConditionObject.signal() throws IllegalMonitorStateException when the synchronizer is not held exclusively and does not throw when it is held
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static class Sync extends AbstractQueuedLongSynchronizer {
        volatile boolean held;

        @Override
        protected boolean isHeldExclusively() {
            return held;
        }
    }

    public static void main(String[] args) {
        Sync sync = new Sync();
        AbstractQueuedLongSynchronizer.ConditionObject condition = sync.newCondition();

        boolean thrown = false;
        try {
            condition.signal();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalMonitorStateException when not held exclusively");
        }

        sync.held = true;
        condition.signal();

        sync.held = false;
        thrown = false;
        try {
            condition.signal();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalMonitorStateException after release");
        }
    }
}
