/*
 * @test
 * @summary Test AbstractQueuedLongSynchronizer.acquire(long) behavior
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static class TestAQLS extends AbstractQueuedLongSynchronizer {
        private static final long serialVersionUID = 1L;

        @Override
        protected boolean tryAcquire(long arg) {
            // For test: always throw UnsupportedOperationException as suggested by WIT
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        TestAQLS sync = new TestAQLS();
        boolean caught = false;
        try {
            sync.acquire(1L);
        } catch (UnsupportedOperationException ex) {
            caught = true;
        }
        if (!caught) {
            throw new Error("Expected UnsupportedOperationException not thrown by acquire");
        }
    }
}
