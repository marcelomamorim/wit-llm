/* @test    @summary Test for releaseShared method in AbstractQueuedLongSynchronizer
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    public static void main(String[] args) {
        testReleaseSharedReturnsTrueWhenTryReleaseSharedSucceeds();
        testReleaseSharedReturnsFalseWhenTryReleaseSharedFails();
    }

    private static void testReleaseSharedReturnsTrueWhenTryReleaseSharedSucceeds() {
        class TestSync extends AbstractQueuedLongSynchronizer {
            private boolean tryReleaseSharedCalled = false;
            @Override
            protected boolean tryReleaseShared(long arg) {
                tryReleaseSharedCalled = true;
                return true;
            }
        }
        TestSync sync = new TestSync();
        boolean result = sync.releaseShared(1L);
        if (!result) {
            throw new AssertionError("Expected true, but got false");
        }
        if (!sync.tryReleaseSharedCalled) {
            throw new AssertionError("tryReleaseShared was not called");
        }
    }

    private static void testReleaseSharedReturnsFalseWhenTryReleaseSharedFails() {
        class TestSync extends AbstractQueuedLongSynchronizer {
            private boolean tryReleaseSharedCalled = false;
            @Override
            protected boolean tryReleaseShared(long arg) {
                tryReleaseSharedCalled = true;
                return false;
            }
        }
        TestSync sync = new TestSync();
        boolean result = sync.releaseShared(1L);
        if (result) {
            throw new AssertionError("Expected false, but got true");
        }
        if (!sync.tryReleaseSharedCalled) {
            throw new AssertionError("tryReleaseShared was not called");
        }
    }
}
