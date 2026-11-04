/*
 * @test
 * @summary Checks ConditionObject.signal() throws IllegalMonitorStateException when the synchronizer is not held exclusively and does not throw when it is held
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

    public static void main(String[] args) {
        testSignalWhenNotHeldThrows();
        testSignalWhenHeldDoesNotThrow();
        testSignalAfterReleaseThrowsAgain();
    }

    static void testSignalWhenNotHeldThrows() {
        Sync sync = new Sync();
        AbstractQueuedSynchronizer.ConditionObject cond = sync.newCondition();
        sync.held = false;
        try {
            cond.signal();
            throw new RuntimeException("Expected IllegalMonitorStateException");
        } catch (IllegalMonitorStateException expected) {
            // expected
        }
    }

    static void testSignalWhenHeldDoesNotThrow() {
        Sync sync = new Sync();
        AbstractQueuedSynchronizer.ConditionObject cond = sync.newCondition();
        sync.held = true;
        cond.signal();
    }

    static void testSignalAfterReleaseThrowsAgain() {
        Sync sync = new Sync();
        AbstractQueuedSynchronizer.ConditionObject cond = sync.newCondition();
        sync.held = true;
        cond.signal();
        sync.held = false;
        try {
            cond.signal();
            throw new RuntimeException("Expected IllegalMonitorStateException after release");
        } catch (IllegalMonitorStateException expected) {
            // expected
        }
    }
}
