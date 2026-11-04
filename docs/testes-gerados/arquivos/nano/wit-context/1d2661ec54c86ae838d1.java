/* @test    @summary Test for AbstractQueuedSynchronizer.signal() method behavior    @run main AbstractQueuedSynchronizerWitupTest */

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
            System.out.println("testSignalWithoutHold: Failed - no exception thrown");
        } catch (IllegalMonitorStateException e) {
            System.out.println("testSignalWithoutHold: Passed - caught expected exception");
        } catch (Exception e) {
            System.out.println("testSignalWithoutHold: Failed - caught unexpected exception: " + e);
        }
    }

    private static void testSignalWithHold() {
        DummyAQS aqs = new DummyAQS();
        try {
            aqs.acquire(1);
            aqs.signal();
            System.out.println("testSignalWithHold: Passed - signal called while holding lock");
        } catch (Exception e) {
            System.out.println("testSignalWithHold: Failed - exception: " + e);
        } finally {
            aqs.release(1);
        }
    }

    private static class DummyAQS extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            // Simulate acquiring lock
            return compareAndSetState(0, 1);
        }

        @Override
        protected boolean tryRelease(int arg) {
            // Simulate releasing lock
            setState(0);
            return true;
        }
    }
}
