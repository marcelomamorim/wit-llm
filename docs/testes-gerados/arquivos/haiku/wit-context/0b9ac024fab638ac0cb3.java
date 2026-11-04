/*
 * @test
 * @summary Behavioral tests for CountDownLatch.await() without timeout
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {

    public static void main(String[] args) throws Exception {
        testAwaitReturnsImmediatelyWhenCountIsZero();
        testAwaitBlocksUntilCountDownToZero();
        testAwaitThrowsInterruptedExceptionWhenInterruptedOnEntry();
    }

    static void testAwaitReturnsImmediatelyWhenCountIsZero() throws Exception {
        CountDownLatch latch = new CountDownLatch(0);
        latch.await();
        if (latch.getCount() != 0) {
            throw new AssertionError("count changed: " + latch.getCount());
        }
    }

    static void testAwaitBlocksUntilCountDownToZero() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        final boolean[] released = new boolean[1];
        final Throwable[] failure = new Throwable[1];
        Thread waiter = new Thread(() -> {
            try {
                latch.await();
                released[0] = true;
            } catch (Throwable t) {
                failure[0] = t;
            }
        });
        waiter.start();
        latch.countDown();
        waiter.join(10000);
        if (waiter.isAlive()) {
            waiter.interrupt();
            throw new AssertionError("await() did not return after countDown()");
        }
        if (failure[0] != null) {
            throw new AssertionError("unexpected exception", failure[0]);
        }
        if (!released[0]) {
            throw new AssertionError("waiter was not released");
        }
        if (latch.getCount() != 0) {
            throw new AssertionError("expected count 0 but was " + latch.getCount());
        }
    }

    static void testAwaitThrowsInterruptedExceptionWhenInterruptedOnEntry() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Thread.currentThread().interrupt();
        try {
            latch.await();
            throw new AssertionError("expected InterruptedException");
        } catch (InterruptedException expected) {
            // expected
        } finally {
            Thread.interrupted();
        }
        if (latch.getCount() != 1) {
            throw new AssertionError("count changed after interrupted await: " + latch.getCount());
        }
    }
}
