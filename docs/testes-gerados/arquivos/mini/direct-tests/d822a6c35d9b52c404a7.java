/*
 * @test
 * @summary Test AbstractQueuedLongSynchronizer.acquireShared(long) behavior
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class AbstractQueuedLongSynchronizerWitupTest {

    static class TestSync extends AbstractQueuedLongSynchronizer {
        private static final long serialVersionUID = 1L;

        @Override
        protected long tryAcquireShared(long arg) {
            // Return negative to force doAcquireShared call
            if (arg == 0) return 1; // success
            if (arg == 1) return 0; // success with zero
            return -1; // failure
        }

        @Override
        protected boolean tryReleaseShared(long arg) {
            return true;
        }
    }

    public static void main(String[] args) {
        TestSync sync = new TestSync();

        // Case 1: tryAcquireShared returns >= 0, acquireShared should not block
        sync.acquireShared(0L);

        // Case 2: tryAcquireShared returns 0, acquireShared should not block
        sync.acquireShared(1L);

        // Case 3: tryAcquireShared returns < 0, acquireShared should call doAcquireShared
        // This will block, so run in a thread and release after a short delay
        final TestSync sync2 = new TestSync();
        Thread t = new Thread(new Runnable() {
            public void run() {
                sync2.acquireShared(2L);
            }
        });
        t.start();

        // Sleep briefly to ensure thread is blocked
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        // Release shared to unblock thread
        sync2.releaseShared(0L);

        try {
            t.join(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        if (t.isAlive()) {
            throw new AssertionError("Thread did not complete acquireShared after releaseShared");
        }
    }
}
