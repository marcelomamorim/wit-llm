/*
 * @test
 * @summary Test for ThreadLocalRandom.nextInt(int) method, focusing on exception behavior and normal output.
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNextIntWithValidBounds();
        testNextIntWithZeroBound();
        testNextIntWithNegativeBound();
    }

    private static void testNextIntWithValidBounds() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        int bound = 10;
        int result = r.nextInt(bound);
        if (result < 0 || result >= bound) {
            throw new AssertionError("Result out of bounds: " + result);
        }
    }

    private static void testNextIntWithZeroBound() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        try {
            r.nextInt(0);
            throw new AssertionError("Expected IllegalArgumentException for zero bound");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testNextIntWithNegativeBound() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        try {
            r.nextInt(-5);
            throw new AssertionError("Expected IllegalArgumentException for negative bound");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
