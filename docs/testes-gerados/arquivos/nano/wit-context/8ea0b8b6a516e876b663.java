/*
 * @test
 * @summary Tests for ThreadLocalRandom.nextDouble(double) method.
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNextDoubleWithPositiveBound();
        testNextDoubleWithZeroBound();
        testNextDoubleWithNegativeBound();
    }

    private static void testNextDoubleWithPositiveBound() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        double bound = 10.0;
        double result = rand.nextDouble(bound);
        if (!(result >= 0.0 && result < bound)) {
            throw new AssertionError("Result should be >= 0.0 and < bound, but was: " + result);
        }
    }

    private static void testNextDoubleWithZeroBound() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        double bound = 0.0;
        try {
            rand.nextDouble(bound);
            throw new AssertionError("Expected IllegalArgumentException for zero bound");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testNextDoubleWithNegativeBound() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        double bound = -5.0;
        try {
            rand.nextDouble(bound);
            throw new AssertionError("Expected IllegalArgumentException for negative bound");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
