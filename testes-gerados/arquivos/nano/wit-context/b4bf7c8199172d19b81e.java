/* @test    @summary Test for SplittableRandom.longs(long streamSize) method with valid and invalid inputs    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testLongsWithPositiveSize();
        testLongsWithZeroSize();
        testLongsWithNegativeSize();
    }

    static void testLongsWithPositiveSize() {
        SplittableRandom sr = new SplittableRandom();
        long size = 10L;
        LongStream stream = sr.longs(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size: " + size + ", but got: " + count);
        }
    }

    static void testLongsWithZeroSize() {
        SplittableRandom sr = new SplittableRandom();
        long size = 0L;
        LongStream stream = sr.longs(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size: " + size + ", but got: " + count);
        }
    }

    static void testLongsWithNegativeSize() {
        SplittableRandom sr = new SplittableRandom();
        long size = -5L;
        try {
            sr.longs(size);
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
