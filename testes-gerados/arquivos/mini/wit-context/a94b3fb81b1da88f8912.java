/*
 * @test
 * @summary Deterministic tests for ThreadLocalRandom.longs(long) method
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testNegativeStreamSizeThrows();
        testZeroStreamSizeReturnsEmptyStream();
        testPositiveStreamSizeReturnsStreamOfCorrectSize();
    }

    private static void testNegativeStreamSizeThrows() {
        try {
            ThreadLocalRandom.current().longs(-1L);
            throw new Error("Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException iae) {
            // Expected exception
        }
    }

    private static void testZeroStreamSizeReturnsEmptyStream() {
        LongStream stream = ThreadLocalRandom.current().longs(0L);
        if (stream == null) {
            throw new Error("Stream should not be null for zero streamSize");
        }
        long count = stream.count();
        if (count != 0L) {
            throw new Error("Stream with zero streamSize should be empty, but count=" + count);
        }
    }

    private static void testPositiveStreamSizeReturnsStreamOfCorrectSize() {
        final long size = 10L;
        LongStream stream = ThreadLocalRandom.current().longs(size);
        if (stream == null) {
            throw new Error("Stream should not be null for positive streamSize");
        }
        long count = stream.count();
        if (count != size) {
            throw new Error("Stream size mismatch: expected " + size + " but got " + count);
        }
    }
}
