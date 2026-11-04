/*
 * @test
 * @summary Test java.util.Random.doubles(long) method for valid and invalid inputs
 * @run main java.util.RandomWitupTest
 */

import java.util.Random;
import java.util.stream.DoubleStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        testDoublesWithValidSize();
        testDoublesWithZeroSize();
        testDoublesWithNegativeSize();
    }

    private static void testDoublesWithValidSize() {
        Random rnd = new Random(12345L);
        long size = 5L;
        DoubleStream stream = rnd.doubles(size);
        // Collect to array and check length
        double[] values = stream.toArray();
        if (values.length != size) {
            throw new AssertionError("Expected stream size " + size + ", but got " + values.length);
        }
        // Check values are within [0.0, Double.MAX_VALUE]
        for (double d : values) {
            if (d < 0.0 || d > Double.MAX_VALUE) {
                throw new AssertionError("Value out of range: " + d);
            }
        }
    }

    private static void testDoublesWithZeroSize() {
        Random rnd = new Random(12345L);
        long size = 0L;
        DoubleStream stream = rnd.doubles(size);
        double[] values = stream.toArray();
        if (values.length != 0) {
            throw new AssertionError("Expected empty stream for size 0, but got length " + values.length);
        }
    }

    private static void testDoublesWithNegativeSize() {
        Random rnd = new Random();
        try {
            rnd.doubles(-1L);
            throw new AssertionError("Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
