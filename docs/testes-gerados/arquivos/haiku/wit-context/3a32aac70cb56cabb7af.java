/*
 * @test
 * @summary Witup-derived tests for SplittableRandom.ints(int,int) range validation
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.IntStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        checkIllegalRange(5, 5);
        checkIllegalRange(10, 3);
        checkIllegalRange(0, Integer.MIN_VALUE);
        checkRangeValues(42L, 0, 10, 1000);
        checkRangeValues(7L, -5, 5, 1000);
        checkRangeValues(3L, 0, 1, 20);
        checkRangeValues(99L, Integer.MIN_VALUE, Integer.MAX_VALUE, 500);
        checkDeterminism();
    }

    private static void checkIllegalRange(int origin, int bound) {
        try {
            new SplittableRandom(1L).ints(origin, bound);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for " + origin + "," + bound);
    }

    private static void checkRangeValues(long seed, int origin, int bound, int count) {
        SplittableRandom r = new SplittableRandom(seed);
        int[] vals = r.ints(origin, bound).limit(count).toArray();
        if (vals.length != count) {
            throw new RuntimeException("unexpected length " + vals.length);
        }
        for (int v : vals) {
            if (v < origin || v >= bound) {
                throw new RuntimeException("value out of range: " + v);
            }
        }
    }

    private static void checkDeterminism() {
        int[] a = new SplittableRandom(123L).ints(0, 1000).limit(50).toArray();
        int[] b = new SplittableRandom(123L).ints(0, 1000).limit(50).toArray();
        if (a.length != b.length) {
            throw new RuntimeException("length mismatch");
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                throw new RuntimeException("non-deterministic at index " + i);
            }
        }
    }
}
