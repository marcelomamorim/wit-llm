/* @test    @summary Tests for java.util.concurrent.CyclicBarrier constructor with parameters    @run main CyclicBarrierWitupTest */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidArguments();
        testConstructorWithZeroParties();
        testConstructorWithNegativeParties();
    }

    private static void testConstructorWithValidArguments() {
        try {
            CyclicBarrier barrier = new CyclicBarrier(3);
            if (barrier == null) {
                throw new AssertionError("CyclicBarrier instance should not be null for valid input.");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input: " + e);
        }
    }

    private static void testConstructorWithZeroParties() {
        try {
            CyclicBarrier barrier = new CyclicBarrier(0);
            throw new AssertionError("Expected IllegalArgumentException for zero parties, but none was thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testConstructorWithNegativeParties() {
        try {
            CyclicBarrier barrier = new CyclicBarrier(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative parties, but none was thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
