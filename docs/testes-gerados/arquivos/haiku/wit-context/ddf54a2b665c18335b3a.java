/*
 * @test
 * @summary Witup-derived tests for Random.doubles(long) argument validation and stream contract
 * @run main RandomWitupTest
 */

import java.util.Arrays;
import java.util.Random;

public class RandomWitupTest {

    public static void main(String[] args) {
        testNegativeSizeThrows();
        testZeroSizeProducesEmptyStream();
        testSizeAndRange();
        testSameSeedSameSequence();
    }

    static void testNegativeSizeThrows() {
        try {
            new Random(1L).doubles(-1L);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for negative streamSize");
    }

    static void testZeroSizeProducesEmptyStream() {
        long count = new Random(2L).doubles(0L).count();
        if (count != 0L) {
            throw new RuntimeException("expected 0 elements but was " + count);
        }
    }

    static void testSizeAndRange() {
        double[] values = new Random(3L).doubles(100L).toArray();
        if (values.length != 100) {
            throw new RuntimeException("expected 100 elements but was " + values.length);
        }
        for (double d : values) {
            if (d < 0.0 || d >= 1.0) {
                throw new RuntimeException("value out of [0,1): " + d);
            }
        }
    }

    static void testSameSeedSameSequence() {
        double[] a = new Random(42L).doubles(50L).toArray();
        double[] b = new Random(42L).doubles(50L).toArray();
        if (!Arrays.equals(a, b)) {
            throw new RuntimeException("same seed produced different sequences");
        }
    }
}
