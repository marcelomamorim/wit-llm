/* @test    @summary Test signal() method of AbstractQueuedSynchronizer */
/* @run main AbstractQueuedSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    public static void main(String[] args) {
        testSignalWithoutHold();
        testSignalWithHold();
    }

    private static void testSignalWithoutHold() {
        AbstractQueuedSynchronizer aqs = new DummyAQS();
        try {
            aqs.signal();
            System.out.println("signal() called without holding lock: FAILED (no exception)");
        } catch (IllegalMonitorStateException e) {
            System.out.println("signal() called without holding lock: PASSED");
        } catch (Throwable t) {
            System.out.println("signal() called without holding lock: FAILED (unexpected exception)");
        }
    }

    private static void testSignalWithHold() {
        DummyAQS aqs = new DummyAQS();
        try {
            aqs.acquire(1);
            aqs.signal();
            System.out.println("signal() called with hold: PASSED");
        } catch (Throwable t) {
            System.out.println("signal() called with hold: FAILED (unexpected exception)");
        } finally {
            aqs.release(1);
        }
    }

    static class DummyAQS extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            setState(1);
            return true;
        }
        @Override
        protected boolean tryRelease(int arg) {
            setState(0);
            return true;
        }
    }
}
