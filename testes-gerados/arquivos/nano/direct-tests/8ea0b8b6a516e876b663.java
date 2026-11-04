/*
 * @test
 * @summary Test for ThreadLocalRandom.nextDouble(double) method.
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNextDoubleWithPositiveBound();
        testNextDoubleWithZeroBound();
        testNextDoubleWithNegativeBound();
        testNextDoubleWithLargeBound();
    }

    static void testNextDoubleWithPositiveBound() {
        double bound = 10.0;
        double result = ThreadLocalRandom.current().nextDouble(bound);
        if (!(result >= 0.0 && result < bound)) {
            throw new AssertionError("Result " + result + " not in [0, " + bound + ")");
        }
    }

    static void testNextDoubleWithZeroBound() {
        try {
            ThreadLocalRandom.current().nextDouble(0.0);
            throw new AssertionError("Expected IllegalArgumentException for zero bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNextDoubleWithNegativeBound() {
        try {
            ThreadLocalRandom.current().nextDouble(-5.0);
            throw new AssertionError("Expected IllegalArgumentException for negative bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNextDoubleWithLargeBound() {
        double bound = Double.MAX_VALUE;
        double result = ThreadLocalRandom.current().nextDouble(bound);
        if (!(result >= 0.0 && result < bound)) {
            throw new AssertionError("Result " + result + " not in [0, " + bound + ")");
        }
    }
}
