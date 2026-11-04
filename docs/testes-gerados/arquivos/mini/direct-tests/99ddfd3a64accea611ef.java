/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.acquire(int) basic behavior
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static final class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 1L;

        Sync(int initial) {
            setState(initial);
        }

        @Override
        protected boolean tryAcquire(int arg) {
            // Acquire only if state is zero
            if (getState() == 0) {
                if (compareAndSetState(0, arg)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        final Sync sync = new Sync(0);

        // Test successful acquire when state is 0
        sync.acquire(1);
        if (sync.getState() != 1) {
            throw new RuntimeException("Failed to acquire lock: state not updated");
        }

        // Test that re-acquire fails (tryAcquire returns false), so thread blocks
        // We test this by starting a thread that tries to acquire and then release
        Thread t = new Thread(new Runnable() {
            public void run() {
                // This will block until main thread releases
                sync.acquire(1);
                sync.release(1);
            }
        });

        t.start();

        // Sleep briefly to ensure thread is blocked on acquire
        Thread.sleep(100);

        // Release lock in main thread to allow other thread to acquire
        sync.release(1);

        // Wait for thread to finish
        t.join(1000);

        if (t.isAlive()) {
            throw new RuntimeException("Thread failed to acquire lock after release");
        }

        // Test that acquire with arg <= 0 still behaves (tryAcquire returns false)
        // We expect it to block, so test with a thread and timeout
        final Sync sync2 = new Sync(1); // locked
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                sync2.acquire(0); // arg=0, tryAcquire returns false
            }
        });
        t2.setDaemon(true);
        t2.start();
        Thread.sleep(100);
        if (!t2.isAlive()) {
            throw new RuntimeException("Thread unexpectedly acquired lock with arg=0");
        }

        // Cleanup: release lock to allow thread to finish
        sync2.release(1);

        System.out.println("PASS");
    }
}
