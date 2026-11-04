/* @test    @summary Test SplittableRandom.nextDouble(double, double) for valid and invalid ranges    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        double origin = 10.0;
        double bound = 20.0;
        for (int i = 0; i < 10; i++) {
            double result = sr.nextDouble(origin, bound);
            if (result < origin || result >= bound) {
                throw new AssertionError("Result out of range: " + result);
            }
        }
    }

    static void testInvalidRange() {
        SplittableRandom sr = new SplittableRandom(54321L);
        double origin = 5.0;
        double bound = 5.0;
        boolean exceptionThrown = false;
        try {
            sr.nextDouble(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for equal bounds")
        }
        double invalidOrigin = 15.0;
        double invalidBound = 10.0;
        exceptionThrown = false;
        try {
            sr.nextDouble(invalidOrigin, invalidBound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for origin > bound")
        }
    }
}
