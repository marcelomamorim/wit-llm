/* @test    @summary Test Random.longs(long, long) for correct range and exception handling    @run main RandomWitupTest */

import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        java.util.Random rand = new java.util.Random(12345L);
        long origin = -100L;
        long bound = 100L;
        LongStream stream = rand.longs(origin, bound);
        long[] values = stream.limit(100).toArray();
        for (long v : values) {
            assert v >= origin && v < bound : "Value out of range: " + v;
        }
    }

    static void testInvalidRange() {
        java.util.Random rand = new java.util.Random(54321L);
        long origin = 50L;
        long bound = 50L;
        try {
            rand.longs(origin, bound).limit(10).toArray();
            assert false : "Expected IllegalArgumentException for equal bounds";
        } catch (IllegalArgumentException e) {
            // expected
        }
        long invalidOrigin = 100L;
        long invalidBound = 50L;
        try {
            rand.longs(invalidOrigin, invalidBound).limit(10).toArray();
            assert false : "Expected IllegalArgumentException for origin > bound";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
