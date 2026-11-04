/* @test    @summary Test SplittableRandom.doubles(long) for correct stream size and behavior    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testDoublesWithPositiveSize();
        testDoublesWithZeroSize();
        testDoublesWithNegativeSize();
    }

    static void testDoublesWithPositiveSize() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long size = 10L;
        DoubleStream stream = sr.doubles(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size: " + size + ", but got: " + count);
        }
        stream.close();
    }

    static void testDoublesWithZeroSize() {
        SplittableRandom sr = new SplittableRandom(54321L);
        long size = 0L;
        DoubleStream stream = sr.doubles(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size: " + size + ", but got: " + count);
        }
        stream.close();
    }

    static void testDoublesWithNegativeSize() {
        SplittableRandom sr = new SplittableRandom(67890L);
        long size = -5L;
        try {
            sr.doubles(size);
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
