/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.acquireShared(int) behavior
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    // Minimal subclass to allow testing acquireShared
    static class TestAQS extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 1L;

        // Override tryAcquireShared to simulate behavior
        @Override
        protected int tryAcquireShared(int arg) {
            // Return negative to force doAcquireShared call
            if (arg == 0) {
                return 1; // success path
            } else if (arg == 1) {
                return -1; // failure path
            } else {
                return 0; // neutral path
            }
        }

        // Override doAcquireShared to track invocation
        @Override
        protected void doAcquireShared(int arg) {
            // Mark that this method was called by setting state
            setState(arg);
        }
    }

    public static void main(String[] args) {
        TestAQS aqs = new TestAQS();

        // Case 1: tryAcquireShared returns positive (1), acquireShared should not call doAcquireShared
        aqs.setState(0);
        aqs.acquireShared(0);
        if (aqs.getState() != 0) {
            throw new Error("acquireShared called doAcquireShared unexpectedly when tryAcquireShared >= 0");
        }

        // Case 2: tryAcquireShared returns negative (-1), acquireShared should call doAcquireShared and set state
        aqs.setState(0);
        aqs.acquireShared(1);
        if (aqs.getState() != 1) {
            throw new Error("acquireShared did not call doAcquireShared as expected when tryAcquireShared < 0");
        }

        // Case 3: tryAcquireShared returns zero (0), acquireShared should not call doAcquireShared
        aqs.setState(0);
        aqs.acquireShared(2);
        if (aqs.getState() != 0) {
            throw new Error("acquireShared called doAcquireShared unexpectedly when tryAcquireShared == 0");
        }

        System.out.println("AbstractQueuedSynchronizerWitupTest passed");
    }
}
