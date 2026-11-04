/* @test    @summary Test SplittableRandom.nextDouble(double) for valid bounds and exception handling    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNextDoubleWithValidBound();
        testNextDoubleWithZeroBound();
        testNextDoubleWithNegativeBound();
    }

    static void testNextDoubleWithValidBound() {
        SplittableRandom sr = new SplittableRandom(12345L);
        double bound = 10.0;
        double result = sr.nextDouble(bound);
        if (!(result >= 0.0 && result < bound)) {
            throw new AssertionError("Result " + result + " not in [0, " + bound + ")");
        }
    }

    static void testNextDoubleWithZeroBound() {
        SplittableRandom sr = new SplittableRandom(54321L);
        try {
            sr.nextDouble(0.0);
            throw new AssertionError("Expected IllegalArgumentException for zero bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNextDoubleWithNegativeBound() {
        SplittableRandom sr = new SplittableRandom(67890L);
        try {
            sr.nextDouble(-5.0);
            throw new AssertionError("Expected IllegalArgumentException for negative bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
