/*
 * @test
 * @summary Test java.util.SplittableRandom.longs(long) method
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testLongsPositiveSize();
        testLongsZeroSize();
        testLongsNegativeSize();
    }

    private static void testLongsPositiveSize() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long streamSize = 10L;
        LongStream stream = sr.longs(streamSize);
        long count = stream.count();
        if (count != streamSize) {
            throw new AssertionError("Expected stream size " + streamSize + ", but got " + count);
        }
    }

    private static void testLongsZeroSize() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long streamSize = 0L;
        LongStream stream = sr.longs(streamSize);
        long count = stream.count();
        if (count != 0L) {
            throw new AssertionError("Expected stream size 0, but got " + count);
        }
    }

    private static void testLongsNegativeSize() {
        SplittableRandom sr = new SplittableRandom(12345L);
        try {
            sr.longs(-1L);
            throw new AssertionError("Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
