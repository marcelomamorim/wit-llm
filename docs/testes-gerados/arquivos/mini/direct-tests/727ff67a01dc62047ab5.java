/*
 * @test
 * @summary Test ThreadLocalRandom.doubles(double, double) for valid and invalid ranges
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
        DoubleStream ds = tlr.doubles(origin, bound);
        // Check that stream is not null and produces values in [origin, bound)
        double val = ds.limit(10).findFirst().orElse(Double.NaN);
        if (Double.isNaN(val)) {
            throw new AssertionError("Stream produced no values");
        }
        if (val < origin || val >= bound) {
            throw new AssertionError("Value " + val + " out of range [" + origin + ", " + bound + ")");
        }
    }

    static void testInvalidRange() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        double origin = 2.0;
        double bound = 1.0;
        try {
            tlr.doubles(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin >= bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            tlr.doubles(origin, origin);
            throw new AssertionError("Expected IllegalArgumentException for origin == bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
