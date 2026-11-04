/*
 * @test
 * @summary Witup-derived tests for SplittableRandom.nextDouble(double) bound validation and range
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNonPositiveBoundsThrow();
        testNaNBoundThrows();
        testResultWithinRange();
        testDeterministicForSameSeed();
        testTinyBoundStaysInRange();
    }

    static void expectIllegalArgument(double bound) {
        try {
            new SplittableRandom(42L).nextDouble(bound);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for bound " + bound);
    }

    static void testNonPositiveBoundsThrow() {
        expectIllegalArgument(0.0);
        expectIllegalArgument(-0.0);
        expectIllegalArgument(-1.0);
        expectIllegalArgument(-Double.MIN_VALUE);
        expectIllegalArgument(Double.NEGATIVE_INFINITY);
    }

    static void testNaNBoundThrows() {
        expectIllegalArgument(Double.NaN);
    }

    static void testResultWithinRange() {
        SplittableRandom r = new SplittableRandom(12345L);
        double bound = 7.5;
        for (int i = 0; i < 10000; i++) {
            double v = r.nextDouble(bound);
            if (!(v >= 0.0 && v < bound)) {
                throw new AssertionError("value out of range: " + v);
            }
        }
    }

    static void testDeterministicForSameSeed() {
        SplittableRandom a = new SplittableRandom(987654321L);
        SplittableRandom b = new SplittableRandom(987654321L);
        for (int i = 0; i < 1000; i++) {
            double va = a.nextDouble(1.0);
            double vb = b.nextDouble(1.0);
            if (Double.doubleToLongBits(va) != Double.doubleToLongBits(vb)) {
                throw new AssertionError("sequences diverge at index " + i);
            }
        }
    }

    static void testTinyBoundStaysInRange() {
        SplittableRandom r = new SplittableRandom(7L);
        for (int i = 0; i < 1000; i++) {
            double v = r.nextDouble(Double.MIN_VALUE);
            if (!(v >= 0.0 && v < Double.MIN_VALUE)) {
                throw new AssertionError("tiny bound value out of range: " + v);
            }
        }
    }
}
