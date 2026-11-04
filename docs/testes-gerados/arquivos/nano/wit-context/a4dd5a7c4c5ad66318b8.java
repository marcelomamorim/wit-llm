/*
 * @test
 * @summary Tests for java.util.SplittableRandom.nextDouble(double)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNextDoubleWithPositiveBound();
        testNextDoubleWithZeroBound();
        testNextDoubleWithNegativeBound();
    }

    private static void testNextDoubleWithPositiveBound() {
        SplittableRandom sr = new SplittableRandom();
        double bound = 10.0;
        double result = sr.nextDouble(bound);
        if (!(result >= 0.0 && result < bound)) {
            throw new AssertionError("Expected result in [0, " + bound + "), but got: " + result);
        }
    }

    private static void testNextDoubleWithZeroBound() {
        SplittableRandom sr = new SplittableRandom();
        try {
            sr.nextDouble(0.0);
            throw new AssertionError("Expected IllegalArgumentException for zero bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNextDoubleWithNegativeBound() {
        SplittableRandom sr = new SplittableRandom();
        double bound = -5.0;
        try {
            sr.nextDouble(bound);
            throw new AssertionError("Expected IllegalArgumentException for negative bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
