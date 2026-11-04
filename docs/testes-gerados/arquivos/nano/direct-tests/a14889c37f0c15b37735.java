/* @test    @summary Test for SplittableRandom.longs(long, long) method    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testLongsWithRange();
        testLongsWithInvalidRange();
    }

    private static void testLongsWithRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long origin = -100L;
        long bound = 100L;
        LongStream stream = sr.longs(origin, bound);
        long[] values = stream.limit(100).toArray();
        for (long v : values) {
            if (v < origin || v >= bound) {
                throw new AssertionError("Value out of range: " + v);
            }
        }
    }

    private static void testLongsWithInvalidRange() {
        SplittableRandom sr = new SplittableRandom(54321L);
        boolean exceptionThrown = false;
        try {
            sr.longs(50L, 50L);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid range");
        }
    }
}
