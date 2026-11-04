/*
 * @test
 * @summary Test ThreadLocalRandom.longs(long, long) for valid and invalid ranges
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        long origin = 10L;
        long bound = 20L;
        LongStream stream = tlr.longs(origin, bound);
        // Check that stream is not null and produces values in [origin, bound)
        long[] values = stream.limit(1000).toArray();
        for (long v : values) {
            if (v < origin || v >= bound) {
                throw new AssertionError("Value " + v + " out of bounds [" + origin + ", " + bound + ")");
            }
        }
    }

    static void testInvalidRange() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        long origin = 20L;
        long bound = 10L;
        try {
            tlr.longs(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException for origin >= bound");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
