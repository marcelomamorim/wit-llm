/*
 * @test
 * @summary Test for ThreadLocalRandom.longs(long streamSize)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testLongsWithPositiveSize();
        testLongsWithZeroSize();
        testLongsWithNegativeSize();
    }

    static void testLongsWithPositiveSize() {
        long size = 5L;
        LongStream stream = ThreadLocalRandom.current().longs(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected count " + size + " but got " + count);
        }
    }

    static void testLongsWithZeroSize() {
        long size = 0L;
        LongStream stream = ThreadLocalRandom.current().longs(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected count " + size + " but got " + count);
        }
    }

    static void testLongsWithNegativeSize() {
        long size = -1L;
        try {
            ThreadLocalRandom.current().longs(size);
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
