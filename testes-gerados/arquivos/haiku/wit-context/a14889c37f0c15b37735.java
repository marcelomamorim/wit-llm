/*
 * @test
 * @summary Witup-generated checks for SplittableRandom.longs(long,long) range validation
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testInvalidRangeThrows(5L, 5L);
        testInvalidRangeThrows(10L, 3L);
        testInvalidRangeThrows(0L, -1L);
        testValidRangeValuesWithinBounds();
        testDeterministicWithSameSeed();
    }

    static void testInvalidRangeThrows(long origin, long bound) {
        SplittableRandom r = new SplittableRandom(1L);
        boolean thrown = false;
        try {
            r.longs(origin, bound);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for origin=" + origin + " bound=" + bound);
        }
    }

    static void testValidRangeValuesWithinBounds() {
        long origin = -10L;
        long bound = 10L;
        long[] vals = new SplittableRandom(7L).longs(origin, bound).limit(1000).toArray();
        if (vals.length != 1000) {
            throw new RuntimeException("unexpected length: " + vals.length);
        }
        for (long v : vals) {
            if (v < origin || v >= bound) {
                throw new RuntimeException("value out of range: " + v);
            }
        }
    }

    static void testDeterministicWithSameSeed() {
        long[] a = new SplittableRandom(42L).longs(0L, 100L).limit(20).toArray();
        long[] b = new SplittableRandom(42L).longs(0L, 100L).limit(20).toArray();
        if (!java.util.Arrays.equals(a, b)) {
            throw new RuntimeException("same seed produced different sequences");
        }
    }
}
