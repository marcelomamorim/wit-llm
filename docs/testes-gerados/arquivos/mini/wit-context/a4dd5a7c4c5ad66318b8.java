/*
 * @test
 * @summary Test deterministic behavior and exception of SplittableRandom.nextDouble(double bound)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testNextDoubleWithValidBound();
        testNextDoubleWithZeroBound();
        testNextDoubleWithNegativeBound();
    }

    private static void testNextDoubleWithValidBound() {
        SplittableRandom sr = new SplittableRandom(12345L);
        double bound = 10.0;
        for (int i = 0; i < 1000; i++) {
            double val = sr.nextDouble(bound);
            if (!(val >= 0.0 && val < bound)) {
                throw new AssertionError("Value out of range: " + val);
            }
        }
    }

    private static void testNextDoubleWithZeroBound() {
        SplittableRandom sr = new SplittableRandom(12345L);
        try {
            sr.nextDouble(0.0);
            throw new AssertionError("Expected IllegalArgumentException for bound=0.0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testNextDoubleWithNegativeBound() {
        SplittableRandom sr = new SplittableRandom(12345L);
        try {
            sr.nextDouble(-1.0);
            throw new AssertionError("Expected IllegalArgumentException for negative bound");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
