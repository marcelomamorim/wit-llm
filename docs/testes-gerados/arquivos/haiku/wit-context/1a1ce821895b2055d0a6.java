/*
 * @test
 * @summary Witup-generated checks for ThreadLocalRandom.doubles(long)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testNegativeSizeThrows();
        testZeroSize();
        testPositiveSize();
    }

    static void testNegativeSizeThrows() {
        long[] sizes = {-1L, Long.MIN_VALUE};
        for (long size : sizes) {
            boolean thrown = false;
            try {
                ThreadLocalRandom.current().doubles(size);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("expected IllegalArgumentException for size " + size);
            }
        }
    }

    static void testZeroSize() {
        long count = ThreadLocalRandom.current().doubles(0L).count();
        if (count != 0L) {
            throw new RuntimeException("expected empty stream, got " + count);
        }
    }

    static void testPositiveSize() {
        DoubleStream s = ThreadLocalRandom.current().doubles(5L);
        double[] values = s.toArray();
        if (values.length != 5) {
            throw new RuntimeException("expected 5 elements, got " + values.length);
        }
        for (double d : values) {
            if (!(d >= 0.0 && d < 1.0)) {
                throw new RuntimeException("value out of range [0,1): " + d);
            }
        }
    }
}
