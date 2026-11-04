/*
 * @test
 * @summary Verifies ThreadLocalRandom.doubles(origin, bound) range validation and bounded output
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testValidRangeProducesValuesWithinBounds();
        testEqualBoundsThrowsIllegalArgumentException();
        testReversedBoundsThrowsIllegalArgumentException();
        testNaNOriginThrowsIllegalArgumentException();
        testNaNBoundThrowsIllegalArgumentException();
    }

    static void testValidRangeProducesValuesWithinBounds() {
        double origin = -2.5;
        double bound = 7.25;
        double[] values = ThreadLocalRandom.current().doubles(origin, bound).limit(1000).toArray();
        if (values.length != 1000) {
            throw new RuntimeException("unexpected count: " + values.length);
        }
        for (double v : values) {
            if (v < origin || v >= bound) {
                throw new RuntimeException("value out of range: " + v);
            }
        }
    }

    static void testEqualBoundsThrowsIllegalArgumentException() {
        expectIllegalArgument(1.0, 1.0);
    }

    static void testReversedBoundsThrowsIllegalArgumentException() {
        expectIllegalArgument(5.0, 2.0);
    }

    static void testNaNOriginThrowsIllegalArgumentException() {
        expectIllegalArgument(Double.NaN, 1.0);
    }

    static void testNaNBoundThrowsIllegalArgumentException() {
        expectIllegalArgument(0.0, Double.NaN);
    }

    static void expectIllegalArgument(double origin, double bound) {
        try {
            ThreadLocalRandom.current().doubles(origin, bound);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for origin=" + origin + " bound=" + bound);
    }
}
