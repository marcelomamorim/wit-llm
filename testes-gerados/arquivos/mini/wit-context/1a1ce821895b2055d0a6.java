/*
 * @test
 * @summary Test ThreadLocalRandom.doubles(long) for valid and invalid inputs
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testDoublesWithValidSize();
        testDoublesWithZeroSize();
        testDoublesWithNegativeSize();
    }

    private static void testDoublesWithValidSize() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        long size = 5L;
        DoubleStream stream = tlr.doubles(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size " + size + ", but got " + count);
        }
    }

    private static void testDoublesWithZeroSize() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        long size = 0L;
        DoubleStream stream = tlr.doubles(size);
        long count = stream.count();
        if (count != 0L) {
            throw new AssertionError("Expected empty stream for size 0, but got " + count);
        }
    }

    private static void testDoublesWithNegativeSize() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        long size = -1L;
        boolean threw = false;
        try {
            tlr.doubles(size);
        } catch (IllegalArgumentException iae) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        }
    }
}
