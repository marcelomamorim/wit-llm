/*
 * @test
 * @summary Test SplittableRandom.doubles(long) for valid and invalid streamSize
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testDoublesWithValidSize();
        testDoublesWithZeroSize();
        testDoublesWithNegativeSize();
    }

    private static void testDoublesWithValidSize() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long size = 5L;
        DoubleStream stream = sr.doubles(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size " + size + ", but got " + count);
        }
    }

    private static void testDoublesWithZeroSize() {
        SplittableRandom sr = new SplittableRandom(67890L);
        long size = 0L;
        DoubleStream stream = sr.doubles(size);
        long count = stream.count();
        if (count != 0L) {
            throw new AssertionError("Expected empty stream for size 0, but got " + count);
        }
    }

    private static void testDoublesWithNegativeSize() {
        SplittableRandom sr = new SplittableRandom();
        long size = -1L;
        try {
            sr.doubles(size);
            throw new AssertionError("Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Expected IllegalArgumentException but got " + t);
        }
    }
}
