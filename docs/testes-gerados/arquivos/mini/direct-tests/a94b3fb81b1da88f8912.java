/*
 * @test
 * @summary Test ThreadLocalRandom.longs(long) method for valid and invalid inputs
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testNegativeStreamSize();
        testZeroStreamSize();
        testPositiveStreamSize();
    }

    static void testNegativeStreamSize() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        try {
            tlr.longs(-1L);
            throw new Error("Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    static void testZeroStreamSize() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        LongStream stream = tlr.longs(0L);
        long count = stream.count();
        if (count != 0L) {
            throw new Error("Expected stream of size 0, got " + count);
        }
    }

    static void testPositiveStreamSize() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        long size = 10L;
        LongStream stream = tlr.longs(size);
        long count = stream.count();
        if (count != size) {
            throw new Error("Expected stream of size " + size + ", got " + count);
        }
    }
}
