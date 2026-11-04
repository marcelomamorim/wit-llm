/*
 * @test
 * @summary Test ThreadLocalRandom.nextDouble(double) for bound validation and rounding behavior
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testBoundZeroOrNegative();
        testResultLessThanBound();
        testRoundingCorrection();
    }

    private static void testBoundZeroOrNegative() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        try {
            rnd.nextDouble(0.0);
            throw new Error("Expected IllegalArgumentException for bound=0.0");
        } catch (IllegalArgumentException expected) {
        }
        try {
            rnd.nextDouble(-1.0);
            throw new Error("Expected IllegalArgumentException for bound=-1.0");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testResultLessThanBound() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        // Run multiple times to check that result is always less than bound
        double bound = 1.0;
        for (int i = 0; i < 1000; i++) {
            double val = rnd.nextDouble(bound);
            if (!(val < bound)) {
                throw new Error("Returned value " + val + " is not less than bound " + bound);
            }
            if (val < 0.0) {
                throw new Error("Returned value " + val + " is negative");
            }
        }
    }

    private static void testRoundingCorrection() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        // The method corrects for rounding by returning a value slightly less than bound if result >= bound
        // We test by forcing a large bound and checking that returned value is never >= bound
        double bound = Double.MIN_VALUE * 2; // very small positive number
        for (int i = 0; i < 1000; i++) {
            double val = rnd.nextDouble(bound);
            if (!(val < bound)) {
                throw new Error("Returned value " + val + " is not less than bound " + bound + " (rounding correction failed)");
            }
        }
    }
}
