/*
 * @test
 * @summary Checks Random.doubles(long) stream size, value range, determinism for equal seeds, and IllegalArgumentException for negative size
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.stream.DoubleStream;

public class RandomWitupTest {

    public static void main(String[] args) {
        testZeroSizeYieldsEmptyStream();
        testStreamSizeMatchesRequested();
        testValuesWithinRange();
        testSameSeedProducesSameValues();
        testNegativeSizeThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testZeroSizeYieldsEmptyStream() {
        long count = new Random(1L).doubles(0L).count();
        check(count == 0L, "doubles(0) should be empty, was " + count);
    }

    static void testStreamSizeMatchesRequested() {
        long[] sizes = {1L, 5L, 100L};
        for (long n : sizes) {
            long count = new Random(3L).doubles(n).count();
            check(count == n, "doubles(" + n + ") count was " + count);
        }
    }

    static void testValuesWithinRange() {
        double[] values = new Random(11L).doubles(1000L).toArray();
        check(values.length == 1000, "unexpected length " + values.length);
        for (int i = 0; i < values.length; i++) {
            double d = values[i];
            check(d >= 0.0 && d < Double.MAX_VALUE, "value out of range at " + i + ": " + d);
        }
    }

    static void testSameSeedProducesSameValues() {
        double[] a = new Random(42L).doubles(50L).toArray();
        double[] b = new Random(42L).doubles(50L).toArray();
        check(a.length == b.length, "length mismatch");
        for (int i = 0; i < a.length; i++) {
            check(Double.compare(a[i], b[i]) == 0, "mismatch at index " + i);
        }
    }

    static void testNegativeSizeThrows() {
        boolean thrown = false;
        try {
            DoubleStream s = new Random(1L).doubles(-1L);
            s.count();
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "doubles(-1) should throw IllegalArgumentException");
    }
}
