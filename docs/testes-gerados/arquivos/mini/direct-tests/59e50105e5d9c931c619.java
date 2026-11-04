/*
 * @test
 * @summary Test java.util.Random.doubles(double, double) for valid and invalid ranges
 * @run main java.util.RandomWitupTest
 */

import java.util.Random;
import java.util.stream.DoubleStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRangeEqual();
        testInvalidRangeReversed();
    }

    private static void testValidRange() {
        Random rnd = new Random(12345L);
        DoubleStream ds = rnd.doubles(1.0, 2.0);
        double[] values = ds.limit(5).toArray();
        for (double v : values) {
            if (!(v >= 1.0 && v < 2.0)) {
                throw new AssertionError("Value " + v + " out of expected range [1.0, 2.0)");
            }
        }
    }

    private static void testInvalidRangeEqual() {
        Random rnd = new Random();
        try {
            rnd.doubles(1.0, 1.0);
            throw new AssertionError("Expected IllegalArgumentException for equal origin and bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidRangeReversed() {
        Random rnd = new Random();
        try {
            rnd.doubles(2.0, 1.0);
            throw new AssertionError("Expected IllegalArgumentException for origin > bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
