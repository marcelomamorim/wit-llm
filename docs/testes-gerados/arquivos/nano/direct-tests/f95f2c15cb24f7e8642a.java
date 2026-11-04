/* @test    @summary Test the release method of AbstractQueuedSynchronizer for correct behavior    @run main AbstractQueuedSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    public static void main(String[] args) {
        testReleaseReturnsTrueWhenTryReleaseSucceeds();
        testReleaseReturnsFalseWhenTryReleaseFails();
    }

    private static void testReleaseReturnsTrueWhenTryReleaseSucceeds() {
        class TestAQS extends AbstractQueuedSynchronizer {
            private boolean tryReleaseCalled = false;
            @Override
            protected boolean tryRelease(int arg) {
                tryReleaseCalled = true;
                return true;
            }
        }
        TestAQS aqs = new TestAQS();
        boolean result = aqs.release(1);
        assert result : "Expected release to return true when tryRelease succeeds";
        assert aqs.tryReleaseCalled : "Expected tryRelease to be called";
    }

    private static void testReleaseReturnsFalseWhenTryReleaseFails() {
        class TestAQS extends AbstractQueuedSynchronizer {
            private boolean tryReleaseCalled = false;
            @Override
            protected boolean tryRelease(int arg) {
                tryReleaseCalled = true;
                return false;
            }
        }
        TestAQS aqs = new TestAQS();
        boolean result = aqs.release(1);
        assert !result : "Expected release to return false when tryRelease fails";
        assert aqs.tryReleaseCalled : "Expected tryRelease to be called";
    }
}
