/* @test    @summary Test CyclicBarrier constructor with valid 'parties' argument    @run main CyclicBarrierWitupTest */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParties();
        testConstructorWithZeroParties();
        testConstructorWithNegativeParties();
    }

    private static void testConstructorWithValidParties() {
        int parties = 3;
        CyclicBarrier barrier = new CyclicBarrier(parties);
        assert barrier != null : "Barrier should not be null";
        // Since no public method exposes parties, test indirectly by behavior
        try {
            for (int i = 0; i < parties; i++) {
                barrier.await();
            }
            // After all parties await, barrier should reset without exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid constructor test", e);
        }
    }

    private static void testConstructorWithZeroParties() {
        try {
            new CyclicBarrier(0);
            throw new AssertionError("Expected exception for zero parties not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testConstructorWithNegativeParties() {
        try {
            new CyclicBarrier(-1);
            throw new AssertionError("Expected exception for negative parties not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
