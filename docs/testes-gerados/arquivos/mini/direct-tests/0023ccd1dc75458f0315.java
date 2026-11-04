/*
 * @test
 * @summary Test ThreadLocalRandom.ints(long) method for valid and invalid inputs
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testNegativeStreamSize();
        testZeroStreamSize();
        testPositiveStreamSize();
    }

    private static void testNegativeStreamSize() {
        try {
            ThreadLocalRandom.current().ints(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testZeroStreamSize() {
        IntStream stream = ThreadLocalRandom.current().ints(0L);
        if (stream == null) {
            throw new RuntimeException("ints(0) returned null stream");
        }
        long count = stream.count();
        if (count != 0L) {
            throw new RuntimeException("ints(0) stream should be empty, but count=" + count);
        }
    }

    private static void testPositiveStreamSize() {
        final long size = 10L;
        IntStream stream = ThreadLocalRandom.current().ints(size);
        if (stream == null) {
            throw new RuntimeException("ints(" + size + ") returned null stream");
        }
        long count = stream.count();
        if (count != size) {
            throw new RuntimeException("ints(" + size + ") stream count expected " + size + ", but was " + count);
        }
    }
}
