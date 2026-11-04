/*
 * @test
 * @summary Checks argument validation and observable output of Random.doubles(double, double)
 * @run main RandomWitupTest
 */

import java.util.Arrays;
import java.util.Random;

public class RandomWitupTest {

    public static void main(String[] args) {
        testEqualBoundsRejected();
        testReversedBoundsRejected();
        testNaNOriginRejected();
        testValuesWithinRangeAndCount();
        testDeterministicSequenceForSameSeed();
    }

    static void expectIllegalArgument(double origin, double bound) {
        try {
            new Random(1L).doubles(origin, bound);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for origin="
                + origin + ", bound=" + bound);
    }

    static void testEqualBoundsRejected() {
        expectIllegalArgument(1.0, 1.0);
    }

    static void testReversedBoundsRejected() {
        expectIllegalArgument(2.0, 1.0);
    }

    static void testNaNOriginRejected() {
        expectIllegalArgument(Double.NaN, 1.0);
    }

    static void testValuesWithinRangeAndCount() {
        double origin = -5.0;
        double bound = 5.0;
        double[] values = new Random(42L).doubles(origin, bound).limit(1000).toArray();
        if (values.length != 1000) {
            throw new RuntimeException("Expected 1000 values but got " + values.length);
        }
        for (double d : values) {
            if (d < origin || d >= bound) {
                throw new RuntimeException("Value out of range: " + d);
            }
        }
    }

    static void testDeterministicSequenceForSameSeed() {
        double[] first = new Random(7L).doubles(0.0, 10.0).limit(50).toArray();
        double[] second = new Random(7L).doubles(0.0, 10.0).limit(50).toArray();
        if (!Arrays.equals(first, second)) {
            throw new RuntimeException("Same seed produced different sequences");
        }
    }
}
