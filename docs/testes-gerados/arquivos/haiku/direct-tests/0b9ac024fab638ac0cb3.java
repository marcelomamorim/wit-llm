/*
 * @test
 * @summary Behavioral tests for CountDownLatch.await() covering immediate return at zero count, blocking until countDown, and interrupt handling
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {

    private static volatile boolean waiterReleased = false;

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testAwaitReturnsImmediatelyWhenCountIsZero();
        testAwaitBlocksUntilCountReachesZero();
        testAwaitThrowsWhenInterruptedBeforeCall();
    }

    static void testAwaitReturnsImmediatelyWhenCountIsZero() throws Exception {
        CountDownLatch latch = new CountDownLatch(0);
        latch.await();
        check(latch.getCount() == 0, "count must remain 0 after await");
        // A second await on an open latch must also return immediately.
        latch.await();
        check(latch.getCount() == 0, "count must remain 0 after second await");
    }

    static void testAwaitBlocksUntilCountReachesZero() throws Exception {
        final CountDownLatch latch = new CountDownLatch(2);
        waiterReleased = false;
        Thread waiter = new Thread(() -> {
            try {
                latch.await();
                waiterReleased = true;
            } catch (InterruptedException e) {
                // not expected in this test
            }
        });
        waiter.start();

        // Count is still 2 for the first countDown: the waiter must stay blocked.
        latch.countDown();
        waiter.join(100);
        check(waiter.isAlive(), "waiter must still block while count is 1");
        check(!waiterReleased, "waiter must not be released before count reaches 0");
        check(latch.getCount() == 1, "count must be 1 after one countDown");

        // Second countDown brings the count to zero and releases the waiter.
        latch.countDown();
        waiter.join(10000);
        check(!waiter.isAlive(), "waiter must finish once count reaches 0");
        check(waiterReleased, "waiter must have passed await");
        check(latch.getCount() == 0, "count must be 0 at the end");
    }

    static void testAwaitThrowsWhenInterruptedBeforeCall() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Thread.currentThread().interrupt();
        boolean thrown = false;
        try {
            latch.await();
        } catch (InterruptedException e) {
            thrown = true;
        }
        check(thrown, "await must throw InterruptedException when the thread is already interrupted");
        check(!Thread.currentThread().isInterrupted(), "interrupt status must be cleared after InterruptedException");
        check(latch.getCount() == 1, "count must be unchanged by an interrupted await");
    }
}
