/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.releaseShared(int) behavior
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    // Minimal subclass to allow calling releaseShared
    static class TestAQS extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryReleaseShared(int arg) {
            // For testing, return false to test releaseShared false path
            return false;
        }
    }

    public static void main(String[] args) {
        TestAQS aqs = new TestAQS();

        // Test releaseShared returns false when tryReleaseShared returns false
        boolean resultFalse = aqs.releaseShared(1);
        if (resultFalse) {
            throw new RuntimeException("releaseShared should return false when tryReleaseShared returns false");
        }

        // Test releaseShared returns true when tryReleaseShared returns true
        TestAQS aqsTrue = new TestAQS() {
            @Override
            protected boolean tryReleaseShared(int arg) {
                return true;
            }

            @Override
            protected void doReleaseShared() {
                // do nothing, just to verify call
                calledDoReleaseShared = true;
            }

            boolean calledDoReleaseShared = false;
        };

        boolean resultTrue = aqsTrue.releaseShared(1);
        if (!resultTrue) {
            throw new RuntimeException("releaseShared should return true when tryReleaseShared returns true");
        }
        if (!((TestAQS) aqsTrue).calledDoReleaseShared) {
            throw new RuntimeException("doReleaseShared was not called when tryReleaseShared returned true");
        }
    }
}
