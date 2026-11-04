/*
 * @test
 * @summary Tests for java.util.concurrent.ThreadLocalRandom.longs(long, long)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        long origin = 10L;
        long bound = 20L;
        LongStream stream = ThreadLocalRandom.current().longs(origin, bound);
        boolean valid = stream.allMatch(val -> val >= origin && val < bound);
        if (!valid) {
            throw new AssertionError("Generated values are outside the specified range") ;
        }
        // Consume stream to avoid resource warnings
        stream.close();
    }

    static void testInvalidRange() {
        long origin = 20L;
        long bound = 10L;
        try {
            ThreadLocalRandom.current().longs(origin, bound).count();
            throw new AssertionError("Expected IllegalArgumentException not thrown") ;
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }
}
