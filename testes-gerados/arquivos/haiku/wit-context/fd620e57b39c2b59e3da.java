/*
 * @test
 * @summary SplittableRandom.ints(long) rejects negative stream sizes and is deterministic for a fixed seed
 * @run main SplittableRandomWitupTest
 */

import java.util.Arrays;
import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        checkNegativeThrows(-1L);
        checkNegativeThrows(Long.MIN_VALUE);
        checkEmptyStream();
        checkSizeHonored();
        checkDeterministic();
    }

    static void checkNegativeThrows(long size) {
        SplittableRandom r = new SplittableRandom(1L);
        boolean thrown = false;
        try {
            r.ints(size);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException for size " + size);
        }
    }

    static void checkEmptyStream() {
        long count = new SplittableRandom(7L).ints(0L).count();
        if (count != 0L) {
            throw new AssertionError("expected empty stream, got " + count);
        }
    }

    static void checkSizeHonored() {
        long count = new SplittableRandom(7L).ints(5L).count();
        if (count != 5L) {
            throw new AssertionError("expected 5 elements, got " + count);
        }
    }

    static void checkDeterministic() {
        int[] a = new SplittableRandom(42L).ints(16L).toArray();
        int[] b = new SplittableRandom(42L).ints(16L).toArray();
        if (!Arrays.equals(a, b)) {
            throw new AssertionError("same seed produced different sequences");
        }
    }
}
