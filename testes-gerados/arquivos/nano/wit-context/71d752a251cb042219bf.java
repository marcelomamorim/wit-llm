/* @test    @summary Test for SplittableRandom.nextDouble(double, double) with valid and invalid inputs    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    private static void testValidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        double origin = 1.0;
        double bound = 2.0;
        double result = sr.nextDouble(origin, bound);
        if (result < origin || result >= bound) {
            throw new AssertionError("Result out of range: " + result);
        }
    }

    private static void testInvalidRange() {
        SplittableRandom sr = new SplittableRandom(54321L);
        double origin = 5.0;
        double bound = 3.0;
        try {
            sr.nextDouble(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for bound <= origin");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
