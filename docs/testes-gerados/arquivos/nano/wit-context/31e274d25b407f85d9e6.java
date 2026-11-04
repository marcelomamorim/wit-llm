/* @test    @summary Test releaseShared method for AbstractQueuedSynchronizer    @run main AbstractQueuedSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    public static void main(String[] args) {
        testReleaseSharedReturnsTrueWhenTryReleaseSharedSucceeds();
        testReleaseSharedReturnsFalseWhenTryReleaseSharedFails();
    }

    private static void testReleaseSharedReturnsTrueWhenTryReleaseSharedSucceeds() {
        AbstractQueuedSynchronizer aqs = new DummyAQS();
        boolean result = aqs.releaseShared(1);
        if (result != true) {
            throw new AssertionError("Expected true when tryReleaseShared succeeds");
        }
    }

    private static void testReleaseSharedReturnsFalseWhenTryReleaseSharedFails() {
        AbstractQueuedSynchronizer aqs = new DummyAQS(false);
        boolean result = aqs.releaseShared(1);
        if (result != false) {
            throw new AssertionError("Expected false when tryReleaseShared fails");
        }
    }

    private static class DummyAQS extends AbstractQueuedSynchronizer {
        private final boolean succeed;

        DummyAQS() {
            this.succeed = true;
        }

        DummyAQS(boolean succeed) {
            this.succeed = succeed;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            return succeed;
        }
    }
}
