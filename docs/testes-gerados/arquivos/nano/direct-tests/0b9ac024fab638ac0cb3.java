/*
 * @test
 * @summary Tests the await() method of CountDownLatch for correct blocking and unblocking behavior.
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {

    public static void main(String[] args) throws Exception {
        testAwaitBlocksUntilCountReachesZero();
        testAwaitReturnsImmediatelyWhenCountIsZero();
    }

    private static void testAwaitBlocksUntilCountReachesZero() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        final boolean[] unblocked = {false};

        Thread t = new Thread(() -> {
            try {
                latch.await();
                unblocked[0] = true;
            } catch (InterruptedException e) {
                // ignore
            }
        });
        t.start();
        Thread.sleep(100); // ensure thread is waiting
        if (unblocked[0]) {
            throw new AssertionError("Thread unblocked prematurely");
        }
        latch.countDown();
        t.join(1000);
        if (!unblocked[0]) {
            throw new AssertionError("Thread did not unblock after countDown()");
        }
    }

    private static void testAwaitReturnsImmediatelyWhenCountIsZero() throws Exception {
        CountDownLatch latch = new CountDownLatch(0);
        long startTime = System.nanoTime();
        latch.await();
        long duration = System.nanoTime() - startTime;
        if (duration > 1_000_000) { // 1 ms in nanoseconds
            throw new AssertionError("await() did not return immediately when count is zero");
        }
    }
}
