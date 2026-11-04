/*
 * @test
 * @summary Test java.util.Random.doubles(long) for valid and invalid streamSize
 * @run main RandomWitupTest
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
        // Count elements to verify stream size
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size " + size + ", but got " + count);
        }
    }

    private static void testDoublesWithZeroSize() {
        Random rnd = new Random(12345L);
        long size = 0L;
        DoubleStream stream = rnd.doubles(size);
        long count = stream.count();
        if (count != 0L) {
            throw new AssertionError("Expected empty stream for size 0, but got " + count);
        }
    }

    private static void testDoublesWithNegativeSize() {
        Random rnd = new Random(12345L);
        long size = -1L;
        try {
            rnd.doubles(size);
            throw new AssertionError("Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
