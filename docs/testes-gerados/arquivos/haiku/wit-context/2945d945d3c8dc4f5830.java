/*
 * @test
 * @summary Verifies that ConditionObject.signalAll throws IllegalMonitorStateException when the synchronizer is not held exclusively and succeeds when it is held
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

public class AbstractQueuedSynchronizerWitupTest {

    static final class Sync extends AbstractQueuedSynchronizer {
        private volatile boolean owned;

        void setOwned(boolean value) {
            owned = value;
        }

        @Override
        protected boolean isHeldExclusively() {
            return owned;
        }
    }

    public static void main(String[] args) {
        Sync sync = new Sync();
        Condition condition = sync.newCondition();

        boolean thrown = false;
        try {
            condition.signalAll();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalMonitorStateException when not held exclusively");
        }

        sync.setOwned(true);
        condition.signalAll();

        sync.setOwned(false);
        thrown = false;
        try {
            condition.signalAll();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalMonitorStateException after releasing ownership");
        }
    }
}
