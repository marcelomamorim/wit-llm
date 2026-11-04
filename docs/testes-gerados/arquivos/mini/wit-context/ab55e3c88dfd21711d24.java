/*
 * @test
 * @summary Test CountDownLatch.countDown() behavior including exception path
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {
    public static void main(String[] args) throws Exception {
        testCountDownNormal();
        testCountDownExceptionPath();
    }

    static void testCountDownNormal() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        // Start a thread that awaits on the latch
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();

        // Count down should release the waiting thread
        latch.countDown();

        // Wait for thread to finish
        t.join(1000);
        if (t.isAlive()) {
            throw new AssertionError("Thread did not finish after countDown");
        }

        // Subsequent await returns immediately
        latch.await();
    }

    static void testCountDownExceptionPath() {
        // According to WIT, countDown() may throw UnsupportedOperationException
        // but current source code calls sync.releaseShared(1) which may throw
        // UnsupportedOperationException if sync is not properly implemented.
        // Since CountDownLatch uses a private sync, this exception is unexpected
        // in normal usage. We test that countDown does not throw in normal case.

        CountDownLatch latch = new CountDownLatch(0); // zero count
        try {
            latch.countDown();
        } catch (UnsupportedOperationException e) {
            throw new AssertionError("countDown threw UnsupportedOperationException unexpectedly", e);
        } catch (Exception e) {
            throw new AssertionError("countDown threw unexpected exception", e);
        }
    }
}
