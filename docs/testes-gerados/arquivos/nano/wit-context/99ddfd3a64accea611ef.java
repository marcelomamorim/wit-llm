/*
 * @test
 * @summary Test the acquire method of AbstractQueuedSynchronizer for normal behavior.
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    public static void main(String[] args) {
        testAcquireNormal();
    }

    public static void testAcquireNormal() {
        AbstractQueuedSynchronizer aqs = new DummyAQS();
        try {
            aqs.acquire(1);
            System.out.println("acquire() executed without exception.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("Caught unexpected exception: " + e);
        }
    }

    static class DummyAQS extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            throw new UnsupportedOperationException();
        }
    }
}
