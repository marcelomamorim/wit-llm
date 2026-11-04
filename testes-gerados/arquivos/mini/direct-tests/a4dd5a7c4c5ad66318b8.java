/*
 * @test
 * @summary Test java.util.SplittableRandom.nextDouble(double bound) method
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testNextDoublePositiveBound();
        testNextDoubleBoundOne();
        testNextDoubleBoundVerySmall();
        testNextDoubleBoundRounding();
        testNextDoubleBoundZeroOrNegative();
    }

    private static void testNextDoublePositiveBound() {
        SplittableRandom sr = new SplittableRandom(12345L);
        double bound = 10.0;
        for (int i = 0; i < 1000; i++) {
            double val = sr.nextDouble(bound);
            if (!(val >= 0.0 && val < bound)) {
                throw new AssertionError("Value out of range: " + val);
            }
        }
    }

    private static void testNextDoubleBoundOne() {
        SplittableRandom sr = new SplittableRandom(67890L);
        double bound = 1.0;
        for (int i = 0; i < 1000; i++) {
            double val = sr.nextDouble(bound);
            if (!(val >= 0.0 && val < bound)) {
                throw new AssertionError("Value out of range for bound=1.0: " + val);
            }
        }
    }

    private static void testNextDoubleBoundVerySmall() {
        SplittableRandom sr = new SplittableRandom(13579L);
        double bound = 1e-10;
        for (int i = 0; i < 1000; i++) {
            double val = sr.nextDouble(bound);
            if (!(val >= 0.0 && val < bound)) {
                throw new AssertionError("Value out of range for very small bound: " + val);
            }
        }
    }

    private static void testNextDoubleBoundRounding() {
        SplittableRandom sr = new SplittableRandom(24680L);
        double bound = Double.longBitsToDouble(Double.doubleToLongBits(5.0) - 1);
        for (int i = 0; i < 1000; i++) {
            double val = sr.nextDouble(bound);
            if (!(val >= 0.0 && val < bound)) {
                throw new AssertionError("Value out of range for rounding test: " + val);
            }
        }
    }

    private static void testNextDoubleBoundZeroOrNegative() {
        SplittableRandom sr = new SplittableRandom();
        try {
            sr.nextDouble(0.0);
            throw new AssertionError("Expected IllegalArgumentException for bound=0.0");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            sr.nextDouble(-1.0);
            throw new AssertionError("Expected IllegalArgumentException for bound=-1.0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
