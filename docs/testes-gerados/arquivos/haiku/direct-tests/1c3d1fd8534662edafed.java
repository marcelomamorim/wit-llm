/*
 * @test
 * @summary signal() must throw IllegalMonitorStateException when the lock is not held exclusively and must succeed when it is held
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.Condition;

public class AbstractQueuedLongSynchronizerWitupTest {

    static class Sync extends AbstractQueuedLongSynchronizer {
        private volatile boolean held;

        void setHeld(boolean value) {
            held = value;
        }

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
        Condition condition = sync.newCondition();

        sync.setHeld(false);
        boolean thrown = false;
        try {
            condition.signal();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        check(thrown, "signal() without holding the lock must throw IllegalMonitorStateException");

        sync.setHeld(true);
        try {
            condition.signal();
        } catch (IllegalMonitorStateException e) {
            throw new RuntimeException("signal() while holding the lock must not throw", e);
        }

        sync.setHeld(false);
        thrown = false;
        try {
            condition.signal();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        check(thrown, "signal() after releasing the lock must throw IllegalMonitorStateException again");
    }
}
