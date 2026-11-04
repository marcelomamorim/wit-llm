/*
 * @test
 * @summary Test SplittableRandom.longs(long) for valid and invalid inputs
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testLongsWithValidSize();
        testLongsWithZeroSize();
        testLongsWithNegativeSize();
    }

    private static void testLongsWithValidSize() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long streamSize = 5L;
        LongStream stream = sr.longs(streamSize);
        long count = stream.count();
        if (count != streamSize) {
            throw new AssertionError("Expected stream size " + streamSize + ", but got " + count);
        }
    }

    private static void testLongsWithZeroSize() {
        SplittableRandom sr = new SplittableRandom(67890L);
        long streamSize = 0L;
        LongStream stream = sr.longs(streamSize);
        long count = stream.count();
        if (count != 0L) {
            throw new AssertionError("Expected empty stream for size 0, but got " + count);
        }
    }

    private static void testLongsWithNegativeSize() {
        SplittableRandom sr = new SplittableRandom();
        long negativeSize = -1L;
        try {
            sr.longs(negativeSize);
            throw new AssertionError("Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Expected IllegalArgumentException but got " + t);
        }
    }
}
