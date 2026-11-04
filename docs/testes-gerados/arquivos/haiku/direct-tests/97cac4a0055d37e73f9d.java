/*
 * @test
 * @summary Tests SplittableRandom.doubles(origin, bound) range validation, bounded output and determinism for a fixed seed
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testEqualOriginAndBoundThrows();
        testOriginGreaterThanBoundThrows();
        testNaNOriginThrows();
        testNaNBoundThrows();
        testValuesWithinRange();
        testReturnedStreamHasRequestedCount();
        testDeterministicForSameSeed();
    }

    static void testEqualOriginAndBoundThrows() {
        expectIllegalArgument(1.0, 1.0);
    }

    static void testOriginGreaterThanBoundThrows() {
        expectIllegalArgument(2.0, 1.0);
    }

    static void testNaNOriginThrows() {
        expectIllegalArgument(Double.NaN, 1.0);
    }

    static void testNaNBoundThrows() {
        expectIllegalArgument(0.0, Double.NaN);
    }

    static void testValuesWithinRange() {
        double origin = -2.5;
        double bound = 3.5;
        double[] values = new SplittableRandom(7L).doubles(origin, bound).limit(1000).toArray();
        if (values.length != 1000) {
            throw new AssertionError("expected 1000 values but got " + values.length);
        }
        for (double v : values) {
            if (!(v >= origin && v < bound)) {
                throw new AssertionError("value out of range: " + v);
            }
        }
    }

    static void testReturnedStreamHasRequestedCount() {
        DoubleStream s = new SplittableRandom(11L).doubles(0.0, 1.0).limit(37);
        long count = s.count();
        if (count != 37L) {
            throw new AssertionError("expected count 37 but got " + count);
        }
    }

    static void testDeterministicForSameSeed() {
        double[] a = new SplittableRandom(42L).doubles(0.0, 1.0).limit(20).toArray();
        double[] b = new SplittableRandom(42L).doubles(0.0, 1.0).limit(20).toArray();
        if (a.length != b.length) {
            throw new AssertionError("length mismatch");
        }
        for (int i = 0; i < a.length; i++) {
            if (Double.doubleToLongBits(a[i]) != Double.doubleToLongBits(b[i])) {
                throw new AssertionError("mismatch at index " + i + ": " + a[i] + " vs " + b[i]);
            }
        }
    }

    static void expectIllegalArgument(double origin, double bound) {
        try {
            new SplittableRandom(1L).doubles(origin, bound);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for origin=" + origin + ", bound=" + bound);
    }
}
