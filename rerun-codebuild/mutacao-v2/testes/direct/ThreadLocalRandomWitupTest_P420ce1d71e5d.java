/*
 * @test
 * @summary Test ThreadLocalRandom.nextDouble(double origin, double bound) for valid and invalid ranges
 * @run main ThreadLocalRandomWitupTest_P420ce1d71e5d
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P420ce1d71e5d {
    private static final String BAD_RANGE = "bound must be greater than origin";

    public static void main(String[] args) {
        testValidRange();
        testInvalidRangeEqual();
        testInvalidRangeReversed();
    }

    private static void testValidRange() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        double origin = 1.0;
        double bound = 2.0;
        for (int i = 0; i < 1000; i++) {
            double val = rnd.nextDouble(origin, bound);
            if (val < origin || val >= bound) {
                throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
            }
        }
    }

    private static void testInvalidRangeEqual() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        double origin = 1.0;
        double bound = 1.0;
        try {
            rnd.nextDouble(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for equal origin and bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidRangeReversed() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        double origin = 2.0;
        double bound = 1.0;
        try {
            rnd.nextDouble(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
