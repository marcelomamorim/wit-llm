/* @test    @summary Test for AbstractQueuedSynchronizer.release(int) method behavior.    @run main AbstractQueuedSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    public static void main(String[] args) {
        testReleaseReturnsTrueWhenTryReleaseSucceeds();
        testReleaseReturnsFalseWhenTryReleaseFails();
    }

    private static void testReleaseReturnsTrueWhenTryReleaseSucceeds() {
        AbstractQueuedSynchronizer aqs = new DummyAQS();
        boolean result = aqs.release(1);
        if (result != true) {
            throw new AssertionError("Expected release to return true when tryRelease succeeds");
        }
    }

    private static void testReleaseReturnsFalseWhenTryReleaseFails() {
        AbstractQueuedSynchronizer aqs = new DummyAQSFail();
        boolean result = aqs.release(1);
        if (result != false) {
            throw new AssertionError("Expected release to return false when tryRelease fails");
        }
    }

    private static class DummyAQS extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryRelease(int arg) {
            return true;
        }
    }

    private static class DummyAQSFail extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryRelease(int arg) {
            return false;
        }
    }
}
