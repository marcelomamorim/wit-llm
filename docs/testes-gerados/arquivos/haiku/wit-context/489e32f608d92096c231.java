/*
 * @test
 * @summary WITUP-derived test: nextInt(origin, bound) must reject bound <= origin and return values in [origin, bound) otherwise
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testValidRangeStaysWithinBounds();
        testEqualOriginAndBoundThrows();
        testBoundLessThanOriginThrows();
        testNegativeRangeBoundLessThanOriginThrows();
    }

    static void testValidRangeStaysWithinBounds() {
        SplittableRandom r = new SplittableRandom(42L);
        for (int i = 0; i < 1000; i++) {
            int v = r.nextInt(-5, 7);
            if (v < -5 || v >= 7) {
                throw new AssertionError("value out of range: " + v);
            }
        }
    }

    static void testEqualOriginAndBoundThrows() {
        SplittableRandom r = new SplittableRandom(1L);
        expectIllegalArgument(r, 5, 5);
    }

    static void testBoundLessThanOriginThrows() {
        SplittableRandom r = new SplittableRandom(2L);
        expectIllegalArgument(r, 10, 3);
    }

    static void testNegativeRangeBoundLessThanOriginThrows() {
        SplittableRandom r = new SplittableRandom(3L);
        expectIllegalArgument(r, 0, -1);
    }

    static void expectIllegalArgument(SplittableRandom r, int origin, int bound) {
        try {
            r.nextInt(origin, bound);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for origin=" + origin + " bound=" + bound);
    }
}
