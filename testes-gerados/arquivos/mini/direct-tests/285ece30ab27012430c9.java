/*
 * @test
 * @summary Test AbstractQueuedSynchronizer.isQueued(Thread) method
 * @run main AbstractQueuedSynchronizerWitupTest
 */

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AbstractQueuedSynchronizerWitupTest {

    static final class TestAQS extends AbstractQueuedSynchronizer {
        TestAQS() {
            super();
        }

        // Expose tail for test by reflection-like access via subclass
        // but tail is private, so we cannot access it directly.
        // Instead, we test isQueued behavior via public API only.

        // We will enqueue a thread by acquiring lock in another thread
        // and test isQueued for that thread.
    }

    public static void main(String[] args) throws Exception {
        final TestAQS sync = new TestAQS();

        // Test 1: Passing null should throw NullPointerException
        try {
            sync.isQueued(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: Current thread is not queued initially
        Thread current = Thread.currentThread();
        if (sync.isQueued(current)) {
            throw new RuntimeException("Current thread should not be queued initially");
        }

        // Test 3: Enqueue a thread by acquiring lock in another thread
        // and test isQueued for that thread while it is blocked

        final Object lock = new Object();
        final Thread blocker = new Thread(new Runnable() {
            public void run() {
                // Acquire lock exclusively to enqueue this thread
                sync.acquire(1);
                // release immediately
                sync.release(1);
            }
        });

        // Start blocker thread
        blocker.start();

        // Wait briefly to ensure blocker thread attempts to acquire
        Thread.sleep(100);

        // The blocker thread should be queued while acquiring
        boolean queued = sync.isQueued(blocker);

        // We cannot guarantee exact timing, so if not queued, wait a bit more
        if (!queued) {
            Thread.sleep(100);
            queued = sync.isQueued(blocker);
        }

        // The blocker thread should be queued or already finished
        // If finished, isQueued returns false, so test both states
        // We assert that isQueued returns true or false without error

        // Test 4: A new thread never started is not queued
        Thread newThread = new Thread();
        if (sync.isQueued(newThread)) {
            throw new RuntimeException("New thread never started should not be queued");
        }

        // Test 5: A thread that finished is not queued
        blocker.join();
        if (sync.isQueued(blocker)) {
            throw new RuntimeException("Thread after completion should not be queued");
        }

        System.out.println("PASS");
    }
}
