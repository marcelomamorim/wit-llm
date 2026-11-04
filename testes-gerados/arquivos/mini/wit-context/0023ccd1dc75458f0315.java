/*
 * @test
 * @summary Test ThreadLocalRandom.ints(long) for valid and invalid inputs
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testIntsWithValidSize();
        testIntsWithZeroSize();
        testIntsWithNegativeSize();
    }

    private static void testIntsWithValidSize() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        long size = 10L;
        IntStream stream = tlr.ints(size);
        // Check that stream is not null and has expected number of elements
        int count = (int) stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size " + size + " but got " + count);
        }
    }

    private static void testIntsWithZeroSize() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        long size = 0L;
        IntStream stream = tlr.ints(size);
        int count = (int) stream.count();
        if (count != 0) {
            throw new AssertionError("Expected empty stream for size 0 but got " + count);
        }
    }

    private static void testIntsWithNegativeSize() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        long size = -1L;
        try {
            tlr.ints(size);
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
