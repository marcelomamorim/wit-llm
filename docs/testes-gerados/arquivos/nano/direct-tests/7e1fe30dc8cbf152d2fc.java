/* @test    @summary Test releaseShared method of AbstractQueuedLongSynchronizer for boolean return and side effects    @run main AbstractQueuedLongSynchronizerWitupTest */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    public static void main(String[] args) {
        testReleaseSharedReturnsTrueWhenTryReleaseSharedSucceeds();
        testReleaseSharedReturnsFalseWhenTryReleaseSharedFails();
    }

    private static void testReleaseSharedReturnsTrueWhenTryReleaseSharedSucceeds() {
        class TestSync extends AbstractQueuedLongSynchronizer {
            private boolean tryReleaseSharedCalled = false;
            private boolean succeed;

            TestSync(boolean succeed) {
                this.succeed = succeed;
            }

            @Override
            protected boolean tryReleaseShared(long arg) {
                tryReleaseSharedCalled = true;
                return succeed;
            }
        }

        TestSync sync = new TestSync(true);
        boolean result = sync.releaseShared(1L);
        assert result : "Expected releaseShared to return true when tryReleaseShared succeeds";
        assert sync.tryReleaseSharedCalled : "Expected tryReleaseShared to be called";
    }

    private static void testReleaseSharedReturnsFalseWhenTryReleaseSharedFails() {
        class TestSync extends AbstractQueuedLongSynchronizer {
            private boolean tryReleaseSharedCalled = false;
            private boolean succeed;

            TestSync(boolean succeed) {
                this.succeed = succeed;
            }

            @Override
            protected boolean tryReleaseShared(long arg) {
                tryReleaseSharedCalled = true;
                return succeed;
            }
        }

        TestSync sync = new TestSync(false);
        boolean result = sync.releaseShared(1L);
        assert !result : "Expected releaseShared to return false when tryReleaseShared fails";
        assert sync.tryReleaseSharedCalled : "Expected tryReleaseShared to be called";
    }
}
