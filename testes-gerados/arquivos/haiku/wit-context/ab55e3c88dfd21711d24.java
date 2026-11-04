/*
 * @test
 * @summary Verifies that CountDownLatch.countDown decrements the count, stops at zero and does not throw when the count is already zero
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);
        check(latch.getCount() == 2L, "initial count must be 2");

        latch.countDown();
        check(latch.getCount() == 1L, "count must be 1 after first countDown");

        latch.countDown();
        check(latch.getCount() == 0L, "count must be 0 after second countDown");

        latch.countDown();
        check(latch.getCount() == 0L, "count must stay 0 when countDown is called at zero");

        CountDownLatch zeroLatch = new CountDownLatch(0);
        check(zeroLatch.getCount() == 0L, "initial count of zero latch must be 0");
        zeroLatch.countDown();
        check(zeroLatch.getCount() == 0L, "countDown on zero latch must keep count at 0");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
