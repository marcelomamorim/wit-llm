/*
 * @test
 * @summary Test java.util.SplittableRandom.longs(long, long) method for valid and invalid ranges
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    private static void testValidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long origin = 10L;
        long bound = 20L;
        LongStream stream = sr.longs(origin, bound);
        // Check that all generated values are within [origin, bound)
        stream.limit(1000).forEach(value -> {
            if (value < origin || value >= bound) {
                throw new AssertionError("Value out of range: " + value);
            }
        });
    }

    private static void testInvalidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long origin = 20L;
        long bound = 10L;
        try {
            sr.longs(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin >= bound");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
