/*
 * @test
 * @summary Checks ConditionObject.signalAll ownership check and no-waiter behavior
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

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testSignalAllWithoutOwnershipThrows() {
        Sync sync = new Sync();
        AbstractQueuedSynchronizer.ConditionObject cond = sync.new ConditionObject();
        sync.held = false;
        boolean thrown = false;
        try {
            cond.signalAll();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        check(thrown, "signalAll without ownership must throw IllegalMonitorStateException");
    }

    static void testSignalAllWithOwnershipNoWaiters() {
        Sync sync = new Sync();
        AbstractQueuedSynchronizer.ConditionObject cond = sync.new ConditionObject();
        sync.held = true;
        cond.signalAll();
        cond.signalAll();
    }

    static void testOwnershipIsCheckedEachCall() {
        Sync sync = new Sync();
        AbstractQueuedSynchronizer.ConditionObject cond = sync.new ConditionObject();
        sync.held = true;
        cond.signalAll();
        sync.held = false;
        boolean thrown = false;
        try {
            cond.signalAll();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        check(thrown, "signalAll after losing ownership must throw IllegalMonitorStateException");
    }

    public static void main(String[] args) {
        testSignalAllWithoutOwnershipThrows();
        testSignalAllWithOwnershipNoWaiters();
        testOwnershipIsCheckedEachCall();
    }
}
