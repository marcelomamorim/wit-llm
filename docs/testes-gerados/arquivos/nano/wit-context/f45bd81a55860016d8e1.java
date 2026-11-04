/*
 * @test
 * @summary Tests for java.util.SplittableRandom.nextLong(long)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNextLongWithPositiveBound();
        testNextLongWithZeroBound();
        testNextLongWithNegativeBound();
    }

    private static void testNextLongWithPositiveBound() {
        SplittableRandom sr = new SplittableRandom();
        long bound = 10L;
        long result = sr.nextLong(bound);
        if (result < 0 || result >= bound) {
            throw new AssertionError("Result should be in [0, " + bound + "), but was: " + result);
        }
    }

    private static void testNextLongWithZeroBound() {
        SplittableRandom sr = new SplittableRandom();
        long bound = 0L;
        try {
            sr.nextLong(bound);
            throw new AssertionError("Expected IllegalArgumentException for bound=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNextLongWithNegativeBound() {
        SplittableRandom sr = new SplittableRandom();
        long bound = -5L;
        try {
            sr.nextLong(bound);
            throw new AssertionError("Expected IllegalArgumentException for negative bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
