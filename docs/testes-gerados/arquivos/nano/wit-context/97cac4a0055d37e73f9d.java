/*
 * @test
 * @summary Tests for java.util.SplittableRandom.doubles(double, double)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testDoublesValidRange();
        testDoublesInvalidRange();
    }

    private static void testDoublesValidRange() {
        SplittableRandom sr = new SplittableRandom();
        double origin = 1.0;
        double bound = 2.0;
        DoubleStream ds = sr.doubles(origin, bound);
        boolean valid = ds.allMatch(d -> d >= origin && d < bound);
        if (!valid) {
            throw new AssertionError("doubles() produced value outside the specified range");
        }
        ds.close();
    }

    private static void testDoublesInvalidRange() {
        SplittableRandom sr = new SplittableRandom();
        double origin = 5.0;
        double bound = 3.0;
        try {
            sr.doubles(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for invalid range")
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
