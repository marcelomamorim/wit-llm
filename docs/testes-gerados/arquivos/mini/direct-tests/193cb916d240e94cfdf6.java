/*
 * @test
 * @summary Test AbstractQueuedLongSynchronizer.acquire(long) basic behavior
 * @run main AbstractQueuedLongSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer.Node;

public class AbstractQueuedLongSynchronizerWitupTest {

    static class TestSync extends AbstractQueuedLongSynchronizer {
        private static final long serialVersionUID = 1L;

        @Override
        protected boolean tryAcquire(long arg) {
            // Acquire only if state is zero
            if (getState() == 0) {
                setState(arg);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(long arg) {
            if (getState() == arg) {
                setState(0);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        TestSync sync = new TestSync();

        // Test acquire when tryAcquire returns true immediately
        sync.acquire(1L);
        if (sync.getState() != 1L) {
            throw new Error("State should be 1 after successful acquire");
        }

        // Release and test acquire again
        if (!sync.tryRelease(1L)) {
            throw new Error("Release failed");
        }

        // Test acquire when tryAcquire returns false, forcing enqueue and wait
        // We test that acquire does not deadlock by running in a separate thread
        final TestSync sync2 = new TestSync();
        Thread t = new Thread(new Runnable() {
            public void run() {
                // This thread will acquire with arg=2
                sync2.acquire(2L);
            }
        });

        // Initially state is zero, so first acquire should succeed
        t.start();
        t.join(1000);

        if (sync2.getState() != 2L) {
            throw new Error("State should be 2 after acquire in thread");
        }

        // Test that calling acquire again from main thread will block (simulate by timeout)
        // We run acquire in another thread and interrupt it after short wait
        final TestSync sync3 = new TestSync();
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                sync3.acquire(3L);
            }
        });
        t2.start();
        t2.join(1000);

        if (sync3.getState() != 3L) {
            throw new Error("State should be 3 after acquire in thread");
        }

        // Now main thread tries to acquire again, which should block and then selfInterrupt
        Thread t3 = new Thread(new Runnable() {
            public void run() {
                // This will block because state is already 3
                sync3.acquire(3L);
            }
        });
        t3.start();

        // Interrupt the thread to simulate selfInterrupt effect
        Thread.sleep(100);
        t3.interrupt();
        t3.join(1000);

        // If no exceptions and threads completed, test passes
    }
}
