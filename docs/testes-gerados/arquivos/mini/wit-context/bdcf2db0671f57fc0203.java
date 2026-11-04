/*
 * @test
 * @summary Test ThreadLocalRandom.longs(long, long) for valid range and exception on invalid range
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    private static void testValidRange() {
        long origin = 10L;
        long bound = 20L;
        LongStream stream = ThreadLocalRandom.current().longs(origin, bound);
        // Check that all generated values are within [origin, bound)
        stream.limit(1000).forEach(value -> {
            if (value < origin || value >= bound) {
                throw new AssertionError("Value out of range: " + value);
            }
        });
    }

    private static void testInvalidRange() {
        long origin = 20L;
        long bound = 10L;
        try {
            ThreadLocalRandom.current().longs(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
