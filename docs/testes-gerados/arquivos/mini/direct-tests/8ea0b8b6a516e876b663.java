/*
 * @test
 * @summary Test ThreadLocalRandom.nextDouble(double bound) for valid and invalid bounds
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
    private static final String BAD_BOUND = "bound must be positive";

    public static void main(String[] args) {
        testNextDoubleWithValidBound();
        testNextDoubleWithInvalidBound();
    }

    private static void testNextDoubleWithValidBound() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        double bound = 10.0;
        for (int i = 0; i < 1000; i++) {
            double val = rnd.nextDouble(bound);
            if (!(val >= 0.0 && val < bound)) {
                throw new AssertionError("Value out of range: " + val + " for bound " + bound);
            }
        }
    }

    private static void testNextDoubleWithInvalidBound() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        double[] invalidBounds = {0.0, -1.0, -Double.MIN_VALUE, -100.0};
        for (double b : invalidBounds) {
            try {
                rnd.nextDouble(b);
                throw new AssertionError("Expected IllegalArgumentException for bound: " + b);
            } catch (IllegalArgumentException iae) {
                // expected
            }
        }
    }
}
