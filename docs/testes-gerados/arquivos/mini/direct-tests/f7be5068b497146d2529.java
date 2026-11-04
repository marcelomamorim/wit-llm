/*
 * @test
 * @summary Test CountDownLatch constructor behavior for valid and invalid counts
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {
    public static void main(String[] args) {
        testValidCount();
        testZeroCount();
        testNegativeCount();
    }

    private static void testValidCount() {
        CountDownLatch latch = new CountDownLatch(1);
        if (latch == null) throw new RuntimeException("CountDownLatch instance is null");
    }

    private static void testZeroCount() {
        CountDownLatch latch = new CountDownLatch(0);
        if (latch == null) throw new RuntimeException("CountDownLatch instance is null");
    }

    private static void testNegativeCount() {
        try {
            new CountDownLatch(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative count");
        } catch (IllegalArgumentException iae) {
            // Expected exception
        }
    }
}
