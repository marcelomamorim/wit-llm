/* @test    @summary Test signalAll method of AbstractQueuedSynchronizer    @run main AbstractQueuedSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    public static void main(String[] args) {
        testSignalAllWithoutHold();
        testSignalAllWithHold();
    }

    private static void testSignalAllWithoutHold() {
        AbstractQueuedSynchronizer aqs = new DummyAQS();
        try {
            aqs.signalAll();
            System.out.println("signalAll() called without holding lock: FAILED (expected exception)");
        } catch (IllegalMonitorStateException e) {
            System.out.println("signalAll() called without holding lock: PASSED");
        } catch (Exception e) {
            System.out.println("signalAll() called without holding lock: FAILED (unexpected exception)");
        }
    }

    private static void testSignalAllWithHold() {
        DummyAQS aqs = new DummyAQS();
        try {
            aqs.acquire(1);
            aqs.signalAll();
            System.out.println("signalAll() called while holding lock: PASSED");
        } catch (Exception e) {
            System.out.println("signalAll() called while holding lock: FAILED (unexpected exception)");
        } finally {
            aqs.release(1);
        }
    }

    private static class DummyAQS extends AbstractQueuedSynchronizer {
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

        @Override
        public void signalAll() {
            super.signalAll();
        }
    }
}
