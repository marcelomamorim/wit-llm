/*
 * @test
 * @summary Verifies ConditionObject.signal() throws IllegalMonitorStateException when the synchronizer is not held exclusively and succeeds when it is held with no waiters
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static class Sync extends AbstractQueuedSynchronizer {
        volatile boolean held;

        @Override
        protected boolean isHeldExclusively() {
            return held;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Sync sync = new Sync();
        AbstractQueuedSynchronizer.ConditionObject cond = sync.new ConditionObject();

        sync.held = false;
        boolean thrown = false;
        try {
            cond.signal();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        check(thrown, "signal() must throw IllegalMonitorStateException when not held exclusively");

        sync.held = true;
        try {
            cond.signal();
            cond.signal();
        } catch (IllegalMonitorStateException e) {
            throw new RuntimeException("signal() must not throw when held exclusively and no waiters exist", e);
        }

        sync.held = false;
        thrown = false;
        try {
            cond.signal();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        check(thrown, "signal() must throw again after the lock is no longer held");
    }
}
