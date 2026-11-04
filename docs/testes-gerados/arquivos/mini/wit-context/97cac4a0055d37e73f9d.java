/*
 * @test
 * @summary Test SplittableRandom.doubles(double, double) for valid range and exception on invalid range
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
        System.out.println("All tests passed.");
    }

    private static void testValidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        double origin = 1.0;
        double bound = 2.0;
        DoubleStream stream = sr.doubles(origin, bound);
        // Check that stream is not null and produces values in [origin, bound)
        if (stream == null) {
            throw new AssertionError("doubles() returned null stream");
        }
        // Take a few values and check range
        final int sampleSize = 10;
        double[] samples = stream.limit(sampleSize).toArray();
        if (samples.length != sampleSize) {
            throw new AssertionError("Stream did not produce expected number of elements");
        }
        for (double d : samples) {
            if (d < origin || d >= bound) {
                throw new AssertionError("Value out of range: " + d);
            }
        }
    }

    private static void testInvalidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        double origin = 2.0;
        double bound = 1.0;
        try {
            sr.doubles(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for bound <= origin");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        // Also test bound == origin
        origin = 1.0;
        bound = 1.0;
        try {
            sr.doubles(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for bound == origin");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
