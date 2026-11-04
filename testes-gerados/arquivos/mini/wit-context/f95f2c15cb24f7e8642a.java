/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.release(int) behavior
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static class TestAQS extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 1L;

        // Override tryRelease to simulate release behavior
        @Override
        protected boolean tryRelease(int arg) {
            // For arg == 0, simulate failure to release
            if (arg == 0) {
                return false;
            }
            // For arg == 1, simulate successful release
            if (arg == 1) {
                // setState to 0 to simulate released state
                setState(0);
                return true;
            }
            // For other values, throw UnsupportedOperationException as baseline suggests
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        TestAQS aqs = new TestAQS();

        // Test release with arg == 1: should return true
        boolean released = aqs.release(1);
        if (!released) {
            throw new Error("release(1) should return true");
        }

        // Test release with arg == 0: should return false
        boolean notReleased = aqs.release(0);
        if (notReleased) {
            throw new Error("release(0) should return false");
        }

        // Test release with arg == 2: should throw UnsupportedOperationException
        boolean threw = false;
        try {
            aqs.release(2);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("release(2) should throw UnsupportedOperationException");
        }
    }
}
