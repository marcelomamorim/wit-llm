/*
 * @test
 * @summary Tests SplittableRandom.doubles(long) for negative size, zero size, stream length, value range and seed determinism
 * @run main SplittableRandomWitupTest
 */

import java.util.Arrays;
import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNegativeSizeThrowsIllegalArgumentException();
        testZeroSizeProducesEmptyStream();
        testStreamLengthAndRange();
        testSameSeedProducesSameValues();
    }

    static void testNegativeSizeThrowsIllegalArgumentException() {
        try {
            new SplittableRandom(1L).doubles(-1L);
            throw new RuntimeException("expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testZeroSizeProducesEmptyStream() {
        long count = new SplittableRandom(42L).doubles(0L).count();
        if (count != 0L) {
            throw new RuntimeException("expected empty stream, got count " + count);
        }
    }

    static void testStreamLengthAndRange() {
        long size = 1000L;
        double[] values = new SplittableRandom(7L).doubles(size).toArray();
        if (values.length != size) {
            throw new RuntimeException("expected length " + size + ", got " + values.length);
        }
        for (double d : values) {
            if (!(d >= 0.0 && d < 1.0)) {
                throw new RuntimeException("value out of [0,1): " + d);
            }
        }
    }

    static void testSameSeedProducesSameValues() {
        double[] a = new SplittableRandom(12345L).doubles(256L).toArray();
        double[] b = new SplittableRandom(12345L).doubles(256L).toArray();
        if (!Arrays.equals(a, b)) {
            throw new RuntimeException("same seed produced different doubles stream");
        }
    }
}
