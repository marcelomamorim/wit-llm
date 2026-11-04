/*
 * @test
 * @summary Test java.util.Random.ints(long) method behavior
 * @run main java.util.RandomWitupTest
 */

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        testNegativeStreamSizeThrows();
        testZeroStreamSizeReturnsEmptyStream();
        testPositiveStreamSizeReturnsCorrectCount();
    }

    private static void testNegativeStreamSizeThrows() {
        Random rnd = new Random(12345L);
        try {
            rnd.ints(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testZeroStreamSizeReturnsEmptyStream() {
        Random rnd = new Random(12345L);
        IntStream stream = rnd.ints(0L);
        if (stream == null) {
            throw new RuntimeException("ints(0) returned null stream");
        }
        long count = stream.count();
        if (count != 0L) {
            throw new RuntimeException("ints(0) stream should be empty but count=" + count);
        }
    }

    private static void testPositiveStreamSizeReturnsCorrectCount() {
        Random rnd = new Random(12345L);
        long size = 10L;
        IntStream stream = rnd.ints(size);
        if (stream == null) {
            throw new RuntimeException("ints(size) returned null stream");
        }
        long count = stream.count();
        if (count != size) {
            throw new RuntimeException("ints(" + size + ") stream count expected " + size + " but was " + count);
        }
    }
}
