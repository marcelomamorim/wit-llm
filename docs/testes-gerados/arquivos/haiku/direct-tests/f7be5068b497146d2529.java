/*
 * @test
 * @summary CountDownLatch constructor accepts non-negative counts and rejects negative counts
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchWitupTest {
    public static void main(String[] args) throws Exception {
        if (!new CountDownLatch(0).await(0, TimeUnit.MILLISECONDS)) {
            throw new AssertionError("latch with count 0 should not block");
        }

        CountDownLatch latch = new CountDownLatch(1);
        if (latch.await(1, TimeUnit.MILLISECONDS)) {
            throw new AssertionError("latch with count 1 should not open before countDown");
        }
        latch.countDown();
        if (!latch.await(1, TimeUnit.MILLISECONDS)) {
            throw new AssertionError("latch should open after countDown reaches zero");
        }

        try {
            new CountDownLatch(-1);
            throw new AssertionError("expected IllegalArgumentException for negative count");
        } catch (IllegalArgumentException e) {
            if (!"count < 0".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }
}
