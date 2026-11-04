/*
 * @test
 * @summary Test java.util.Random.longs(long randomNumberOrigin, long randomNumberBound) for valid and invalid ranges
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.stream.LongStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    private static void testValidRange() {
        Random rnd = new Random(12345L);
        long origin = 10L;
        long bound = 20L;
        LongStream stream = rnd.longs(origin, bound);
        // Check that all generated values are within [origin, bound)
        stream.limit(1000).forEach(value -> {
            if (value < origin || value >= bound) {
                throw new AssertionError("Value out of range: " + value);
            }
        });
    }

    private static void testInvalidRange() {
        Random rnd = new Random();
        long origin = 100L;
        long bound = 50L;
        boolean threw = false;
        try {
            rnd.longs(origin, bound);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for bound <= origin");
        }
    }
}
