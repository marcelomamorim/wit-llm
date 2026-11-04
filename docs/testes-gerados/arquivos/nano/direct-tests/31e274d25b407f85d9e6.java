/* @test    @summary Test releaseShared method of AbstractQueuedSynchronizer for correct behavior    @run main AbstractQueuedSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    public static void main(String[] args) {
        testReleaseSharedReturnsTrueWhenTryReleaseSharedSucceeds();
        testReleaseSharedReturnsFalseWhenTryReleaseSharedFails();
    }

    private static void testReleaseSharedReturnsTrueWhenTryReleaseSharedSucceeds() {
        class TestAQS extends AbstractQueuedSynchronizer {
            private static final long serialVersionUID = 1L;
            private boolean tryReleaseSharedCalled = false;

            @Override
            protected boolean tryReleaseShared(int arg) {
                tryReleaseSharedCalled = true;
                return true;
            }
        }
        TestAQS aq = new TestAQS();
        boolean result = aq.releaseShared(1);
        assert result : "Expected releaseShared to return true when tryReleaseShared returns true";
        assert aq.tryReleaseSharedCalled : "tryReleaseShared should be called";
    }

    private static void testReleaseSharedReturnsFalseWhenTryReleaseSharedFails() {
        class TestAQS extends AbstractQueuedSynchronizer {
            private static final long serialVersionUID = 1L;
            private boolean tryReleaseSharedCalled = false;

            @Override
            protected boolean tryReleaseShared(int arg) {
                tryReleaseSharedCalled = true;
                return false;
            }
        }
        TestAQS aq = new TestAQS();
        boolean result = aq.releaseShared(1);
        assert !result : "Expected releaseShared to return false when tryReleaseShared returns false";
        assert aq.tryReleaseSharedCalled : "tryReleaseShared should be called";
    }
}
