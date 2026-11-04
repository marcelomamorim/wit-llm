/*
 * @test
 * @summary Black-box checks for SplittableRandom.doubles() no-arg stream: range, length and seed determinism
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testLimitedStreamLengthAndRange();
        testZeroLimitGivesEmptyArray();
        testSameSeedGivesSameSequence();
        testStreamIsNotNullAndUsable();
    }

    static void testLimitedStreamLengthAndRange() {
        SplittableRandom r = new SplittableRandom(42L);
        double[] values = r.doubles().limit(1000).toArray();
        check(values.length == 1000, "expected 1000 values but got " + values.length);
        for (int i = 0; i < values.length; i++) {
            double v = values[i];
            check(v >= 0.0 && v < 1.0, "value out of [0,1): " + v + " at index " + i);
        }
    }

    static void testZeroLimitGivesEmptyArray() {
        SplittableRandom r = new SplittableRandom(7L);
        double[] values = r.doubles().limit(0).toArray();
        check(values.length == 0, "expected empty array but got length " + values.length);
    }

    static void testSameSeedGivesSameSequence() {
        double[] a = new SplittableRandom(12345L).doubles().limit(256).toArray();
        double[] b = new SplittableRandom(12345L).doubles().limit(256).toArray();
        check(a.length == b.length, "length mismatch for same seed");
        for (int i = 0; i < a.length; i++) {
            check(Double.doubleToLongBits(a[i]) == Double.doubleToLongBits(b[i]),
                  "same seed produced different value at index " + i);
        }
    }

    static void testStreamIsNotNullAndUsable() {
        DoubleStream s = new SplittableRandom(1L).doubles();
        check(s != null, "doubles() returned null");
        long count = s.limit(10).count();
        check(count == 10L, "expected count 10 but got " + count);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
