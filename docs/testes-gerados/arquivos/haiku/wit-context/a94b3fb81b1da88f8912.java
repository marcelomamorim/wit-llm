/*
 * @test
 * @summary Verifies ThreadLocalRandom.longs(long) rejects negative sizes and produces the requested number of elements
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNegativeSizeThrows(-1L);
        testNegativeSizeThrows(Long.MIN_VALUE);
        testZeroSize();
        testPositiveSizes();
    }

    static void testNegativeSizeThrows(long size) {
        boolean thrown = false;
        try {
            ThreadLocalRandom.current().longs(size);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for size " + size);
        }
    }

    static void testZeroSize() {
        LongStream s = ThreadLocalRandom.current().longs(0L);
        long count = s.count();
        if (count != 0L) {
            throw new RuntimeException("Expected 0 elements, got " + count);
        }
    }

    static void testPositiveSizes() {
        long[] sizes = {1L, 5L, 100L};
        for (long size : sizes) {
            LongStream s = ThreadLocalRandom.current().longs(size);
            long count = s.count();
            if (count != size) {
                throw new RuntimeException("Expected " + size + " elements, got " + count);
            }
        }
    }
}
