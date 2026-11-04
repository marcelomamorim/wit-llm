/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.releaseShared(int) behavior
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static class TestAQS extends AbstractQueuedSynchronizer {
        private volatile boolean released = false;

        @Override
        protected boolean tryReleaseShared(int arg) {
            // Release only if arg is positive
            if (arg > 0) {
                released = true;
                return true;
            }
            return false;
        }

        boolean isReleased() {
            return released;
        }

        @Override
        protected boolean tryAcquireShared(int arg) {
            return 1; // dummy implementation
        }
    }

    public static void main(String[] args) {
        TestAQS aqs = new TestAQS();

        // Test releaseShared returns true and calls doReleaseShared when tryReleaseShared returns true
        boolean releasedResult = aqs.releaseShared(1);
        if (!releasedResult) {
            throw new RuntimeException("releaseShared(1) should return true");
        }
        if (!aqs.isReleased()) {
            throw new RuntimeException("tryReleaseShared(1) should have set released flag");
        }

        // Test releaseShared returns false when tryReleaseShared returns false
        TestAQS aqs2 = new TestAQS();
        boolean releasedResult2 = aqs2.releaseShared(0);
        if (releasedResult2) {
            throw new RuntimeException("releaseShared(0) should return false");
        }
        if (aqs2.isReleased()) {
            throw new RuntimeException("tryReleaseShared(0) should not set released flag");
        }

        System.out.println("PASS");
    }
}
