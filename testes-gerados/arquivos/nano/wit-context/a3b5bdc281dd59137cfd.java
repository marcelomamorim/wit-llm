/*
 * @test
 * @summary Tests for java.util.SplittableRandom.doubles(long)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testDoublesWithPositiveSize();
        testDoublesWithZeroSize();
        testDoublesWithNegativeSize();
    }

    static void testDoublesWithPositiveSize() {
        SplittableRandom sr = new SplittableRandom();
        long size = 10L;
        DoubleStream stream = sr.doubles(size);
        try {
            long count = stream.count();
            if (count != size) {
                throw new AssertionError("Expected stream size: " + size + ", but got: " + count);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for positive size", e);
        }
    }

    static void testDoublesWithZeroSize() {
        SplittableRandom sr = new SplittableRandom();
        long size = 0L;
        DoubleStream stream = sr.doubles(size);
        try {
            long count = stream.count();
            if (count != 0) {
                throw new AssertionError("Expected stream size: 0, but got: " + count);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero size", e);
        }
    }

    static void testDoublesWithNegativeSize() {
        SplittableRandom sr = new SplittableRandom();
        long size = -5L;
        try {
            DoubleStream stream = sr.doubles(size);
            stream.count();
            throw new AssertionError("Expected IllegalArgumentException for negative size, but no exception was thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for negative size", e);
        }
    }
}
