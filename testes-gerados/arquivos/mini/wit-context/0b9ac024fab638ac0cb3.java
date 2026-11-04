/*
 * @test
 * @summary Test CountDownLatch.await() behavior including interrupt handling and normal wait
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {
    public static void main(String[] args) throws Exception {
        testAwaitBlocksUntilCountZero();
        testAwaitRespondsToInterrupt();
    }

    static void testAwaitBlocksUntilCountZero() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        Thread waiter = new Thread(new Runnable() {
            public void run() {
                try {
                    latch.await();
                    // If await returns, count must be zero
                    if (latch.getCount() != 0) {
                        throw new AssertionError("Latch count not zero after await returned");
                    }
                } catch (InterruptedException e) {
                    throw new AssertionError("Unexpected InterruptedException", e);
                }
            }
        });
        waiter.start();

        // Sleep briefly to ensure waiter is waiting
        Thread.sleep(100);

        // Count down to zero, releasing waiter
        latch.countDown();

        // Wait for waiter to finish
        waiter.join(1000);

        if (waiter.isAlive()) {
            throw new AssertionError("Thread did not return from await after countDown");
        }
    }

    static void testAwaitRespondsToInterrupt() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        Thread waiter = new Thread(new Runnable() {
            public void run() {
                try {
                    latch.await();
                    throw new AssertionError("await() should have thrown InterruptedException");
                } catch (InterruptedException e) {
                    // Expected path
                }
            }
        });
        waiter.start();

        // Sleep briefly to ensure waiter is waiting
        Thread.sleep(100);

        // Interrupt the waiting thread
        waiter.interrupt();

        // Wait for waiter to finish
        waiter.join(1000);

        if (waiter.isAlive()) {
            throw new AssertionError("Thread did not exit await after interrupt");
        }
    }
}
