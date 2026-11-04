/*
 * @test
 * @summary Test CountDownLatch await() method behavior
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {
    public static void main(String[] args) throws Exception {
        testAwaitReturnsImmediatelyWhenCountIsZero();
        testAwaitBlocksUntilCountDown();
        testAwaitInterruptible();
    }

    static void testAwaitReturnsImmediatelyWhenCountIsZero() throws Exception {
        CountDownLatch latch = new CountDownLatch(0);
        // Should return immediately without blocking
        latch.await();
    }

    static void testAwaitBlocksUntilCountDown() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Unexpected interrupt", e);
                }
            }
        });
        t.start();

        // Give thread time to start and block
        Thread.sleep(100);

        // Thread should be blocked, so it should be alive
        if (!t.isAlive()) {
            throw new RuntimeException("Thread should be blocked on await");
        }

        latch.countDown();

        // Wait for thread to finish
        t.join(1000);

        if (t.isAlive()) {
            throw new RuntimeException("Thread did not finish after countDown");
        }
    }

    static void testAwaitInterruptible() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        final Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    latch.await();
                    throw new RuntimeException("await() returned unexpectedly");
                } catch (InterruptedException e) {
                    // Expected
                }
            }
        });
        t.start();

        // Give thread time to start and block
        Thread.sleep(100);

        // Interrupt the thread while it is waiting
        t.interrupt();

        // Wait for thread to finish
        t.join(1000);

        if (t.isAlive()) {
            throw new RuntimeException("Thread did not finish after interrupt");
        }
    }
}
