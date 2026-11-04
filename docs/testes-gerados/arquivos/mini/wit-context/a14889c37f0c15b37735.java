/*
 * @test
 * @summary Test SplittableRandom.longs(long randomNumberOrigin, long randomNumberBound) for valid range and exception
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRangeThrows();
        System.out.println("All tests passed.");
    }

    private static void testValidRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long origin = 10L;
        long bound = 20L;
        LongStream stream = sr.longs(origin, bound);
        // Check that stream produces values within [origin, bound)
        stream.limit(1000).forEach(value -> {
            if (value < origin || value >= bound) {
                throw new AssertionError("Value out of range: " + value);
            }
        });
    }

    private static void testInvalidRangeThrows() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long origin = 20L;
        long bound = 10L;
        boolean threw = false;
        try {
            sr.longs(origin, bound);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for bound <= origin");
        }
    }
}
