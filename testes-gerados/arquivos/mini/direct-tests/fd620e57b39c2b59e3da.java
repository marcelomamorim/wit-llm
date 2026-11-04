/*
 * @test
 * @summary Test java.util.SplittableRandom.ints(long) method behavior
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.IntStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testIntsWithValidSize();
        testIntsWithZeroSize();
        testIntsWithNegativeSize();
    }

    private static void testIntsWithValidSize() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long size = 10L;
        IntStream stream = sr.ints(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size " + size + ", but got " + count);
        }
    }

    private static void testIntsWithZeroSize() {
        SplittableRandom sr = new SplittableRandom(67890L);
        IntStream stream = sr.ints(0L);
        long count = stream.count();
        if (count != 0L) {
            throw new AssertionError("Expected empty stream for size 0, but got " + count);
        }
    }

    private static void testIntsWithNegativeSize() {
        SplittableRandom sr = new SplittableRandom();
        try {
            sr.ints(-1L);
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
