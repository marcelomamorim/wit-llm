/*
 * @test
 * @summary Test for AbstractQueuedLongSynchronizer.signal() method behavior.
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    public static void main(String[] args) {
        testSignalWithoutHold();
        testSignalWithHold();
    }

    private static void testSignalWithoutHold() {
        AbstractQueuedLongSynchronizer synchronizer = new DummyAQLS();
        try {
            synchronizer.signal();
            System.out.println("testSignalWithoutHold: Passed (no exception)");
        } catch (UnsupportedOperationException e) {
            System.out.println("testSignalWithoutHold: Failed - UnsupportedOperationException")
        } catch (Exception e) {
            System.out.println("testSignalWithoutHold: Failed - " + e);
        }
    }

    private static void testSignalWithHold() {
        DummyAQLS synchronizer = new DummyAQLS();
        synchronizer.setHeld(true);
        try {
            synchronizer.signal();
            System.out.println("testSignalWithHold: Failed - no exception");
        } catch (IllegalMonitorStateException e) {
            System.out.println("testSignalWithHold: Passed (IllegalMonitorStateException)");
        } catch (UnsupportedOperationException e) {
            System.out.println("testSignalWithHold: Failed - UnsupportedOperationException")
        } catch (Exception e) {
            System.out.println("testSignalWithHold: Failed - " + e);
        }
    }

    static class DummyAQLS extends AbstractQueuedLongSynchronizer {
        private boolean held = false;
        public void setHeld(boolean hold) {
            this.held = hold;
        }
        @Override
        protected boolean isHeldExclusively() {
            return held;
        }
    }
}
