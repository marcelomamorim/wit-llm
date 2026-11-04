/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.acquireShared(int) behavior
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 1L;

        Sync(int state) {
            setState(state);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            // Return positive if state >= arg, else negative
            return (getState() >= arg) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            // Decrement state by arg
            for (;;) {
                int s = getState();
                int next = s - arg;
                if (compareAndSetState(s, next))
                    return true;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // Test case 1: tryAcquireShared returns >= 0, acquireShared returns immediately
        Sync sync1 = new Sync(5);
        sync1.acquireShared(3); // should not block

        // Test case 2: tryAcquireShared returns < 0, acquireShared blocks until release
        final Sync sync2 = new Sync(0);

        Thread t = new Thread(new Runnable() {
            public void run() {
                sync2.acquireShared(1); // will block until state >= 1
            }
        });
        t.start();

        // Sleep briefly to ensure thread is blocked
        Thread.sleep(100);
        if (t.getState() == Thread.State.TERMINATED) {
            throw new RuntimeException("Thread should be blocked but terminated");
        }

        // Release shared lock to unblock thread
        sync2.releaseShared(1);

        // Wait for thread to finish
        t.join(1000);
        if (t.isAlive()) {
            throw new RuntimeException("Thread did not unblock after releaseShared");
        }

        // Test case 3: acquireShared with zero arg, should succeed if state >= 0
        Sync sync3 = new Sync(0);
        sync3.acquireShared(0); // should not block

        System.out.println("PASS");
    }
}
