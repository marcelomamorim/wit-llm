/*
 * @test
 * @summary Tests SplittableRandom.doubles() range, determinism, size and sequential mode
 * @run main SplittableRandomWitupTest
 */

import java.util.Arrays;
import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testValuesWithinRange();
        testLimitedCount();
        testDeterminismWithSameSeed();
        testDifferentSeedsProduceDifferentValues();
        testStreamIsSequential();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValuesWithinRange() {
        double[] values = new SplittableRandom(7L).doubles().limit(1000).toArray();
        check(values.length == 1000, "expected 1000 values");
        for (int i = 0; i < values.length; i++) {
            double d = values[i];
            check(d >= 0.0 && d < Double.MAX_VALUE, "value out of range at " + i + ": " + d);
        }
    }

    static void testLimitedCount() {
        long count = new SplittableRandom(11L).doubles().limit(10).count();
        check(count == 10L, "expected count 10 but was " + count);
    }

    static void testDeterminismWithSameSeed() {
        double[] a = new SplittableRandom(42L).doubles().limit(20).toArray();
        double[] b = new SplittableRandom(42L).doubles().limit(20).toArray();
        check(Arrays.equals(a, b), "same seed must produce same doubles stream");
    }

    static void testDifferentSeedsProduceDifferentValues() {
        double[] a = new SplittableRandom(1L).doubles().limit(20).toArray();
        double[] b = new SplittableRandom(2L).doubles().limit(20).toArray();
        check(!Arrays.equals(a, b), "different seeds should produce different doubles streams");
    }

    static void testStreamIsSequential() {
        check(!new SplittableRandom(3L).doubles().isParallel(), "doubles() stream must not be parallel");
    }
}
