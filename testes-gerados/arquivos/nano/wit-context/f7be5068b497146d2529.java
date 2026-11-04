/*
 * @test    @summary Test for CountDownLatch constructor with valid and invalid inputs
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {

    public static void main(String[] args) {
        testValidCount();
        testNegativeCount();
    }

    private static void testValidCount() {
        try {
            CountDownLatch latch = new CountDownLatch(3);
            assert latch != null : "Latch should be created with positive count";
            System.out.println("testValidCount passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testValidCount", e);
        }
    }

    private static void testNegativeCount() {
        try {
            CountDownLatch latch = new CountDownLatch(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative count");
        } catch (IllegalArgumentException e) {
            System.out.println("testNegativeCount passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type in testNegativeCount", e);
        }
    }
}
