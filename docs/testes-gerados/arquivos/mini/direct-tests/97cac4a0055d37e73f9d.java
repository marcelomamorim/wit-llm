/*
 * @test
 * @summary Test SplittableRandom.doubles(double, double) method behavior
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRangeEqual();
        testInvalidRangeReversed();
    }

    static void testValidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        double origin = 1.0;
        double bound = 5.0;
        DoubleStream stream = sr.doubles(origin, bound);
        // Take a few values and check they are within [origin, bound)
        double[] values = stream.limit(10).toArray();
        for (int i = 0; i < values.length; i++) {
            double v = values[i];
            if (!(v >= origin && v < bound)) {
                throw new AssertionError("Value out of range: " + v);
            }
        }
    }

    static void testInvalidRangeEqual() {
        SplittableRandom sr = new SplittableRandom(12345L);
        double origin = 2.0;
        double bound = 2.0;
        try {
            sr.doubles(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for equal origin and bound");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static void testInvalidRangeReversed() {
        SplittableRandom sr = new SplittableRandom(12345L);
        double origin = 5.0;
        double bound = 3.0;
        try {
            sr.doubles(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
