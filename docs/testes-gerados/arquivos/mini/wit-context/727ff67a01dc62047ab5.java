/*
 * @test
 * @summary Test ThreadLocalRandom.doubles(double, double) for valid range and exception on invalid range
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        double origin = 1.0;
        double bound = 2.0;
        DoubleStream stream = tlr.doubles(origin, bound);
        // Check that stream is not null and produces values in [origin, bound)
        if (stream == null) {
            throw new AssertionError("doubles() returned null stream");
        }
        // Test a few values from the stream
        final int sampleSize = 10;
        double[] samples = stream.limit(sampleSize).toArray();
        for (double d : samples) {
            if (d < origin || d >= bound) {
                throw new AssertionError("Value out of bounds: " + d + ", expected in [" + origin + ", " + bound + ")");
            }
        }
    }

    static void testInvalidRange() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        double origin = 2.0;
        double bound = 1.0;
        boolean threw = false;
        try {
            tlr.doubles(origin, bound);
        } catch (IllegalArgumentException iae) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for bound <= origin");
        }
    }
}
