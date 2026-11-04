/*
 * @test
 * @summary Test CountDownLatch constructor for valid and invalid counts
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {
    public static void main(String[] args) {
        testValidCount();
        testInvalidCount();
        System.out.println("All tests passed.");
    }

    private static void testValidCount() {
        // Test with zero count - should succeed
        CountDownLatch latchZero = new CountDownLatch(0);
        if (latchZero == null) throw new AssertionError("CountDownLatch instance is null");

        // Test with positive count - should succeed
        CountDownLatch latchPositive = new CountDownLatch(5);
        if (latchPositive == null) throw new AssertionError("CountDownLatch instance is null");
    }

    private static void testInvalidCount() {
        // Test with negative count - should throw IllegalArgumentException
        boolean thrown = false;
        try {
            new CountDownLatch(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative count");
        }
    }
}
