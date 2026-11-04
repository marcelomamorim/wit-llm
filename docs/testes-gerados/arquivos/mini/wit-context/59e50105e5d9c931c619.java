/*
 * @test
 * @summary Test java.util.Random.doubles(double, double) for valid range and exception on invalid range
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.stream.DoubleStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRangeThrows();
        System.out.println("PASS");
    }

    private static void testValidRange() {
        Random rnd = new Random(12345L);
        double origin = 1.0;
        double bound = 5.0;
        DoubleStream stream = rnd.doubles(origin, bound);
        // Check that stream is not null and produces values in [origin, bound)
        double[] values = stream.limit(10).toArray();
        for (double v : values) {
            if (v < origin || v >= bound) {
                throw new AssertionError("Value out of range: " + v);
            }
        }
    }

    private static void testInvalidRangeThrows() {
        Random rnd = new Random(12345L);
        double origin = 5.0;
        double bound = 1.0;
        boolean threw = false;
        try {
            rnd.doubles(origin, bound);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for bound <= origin");
        }
    }
}
