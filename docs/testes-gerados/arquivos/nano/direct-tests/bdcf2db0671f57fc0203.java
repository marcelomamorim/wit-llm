/*
 * @test
 * @summary Test ThreadLocalRandom.longs(long, long) for correct range and exception
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        long origin = 10L;
        long bound = 20L;
        LongStream stream = java.util.concurrent.ThreadLocalRandom.current().longs(origin, bound);
        long count = stream.limit(1000).filter(n -> n >= origin && n < bound).count();
        if (count != 1000) {
            throw new AssertionError("Generated values out of range") ;
        }
    }

    static void testInvalidRange() {
        try {
            java.util.concurrent.ThreadLocalRandom.current().longs(20L, 10L).count();
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
