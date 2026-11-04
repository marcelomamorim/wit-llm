/*
 * @test
 * @summary Tests Random.ints(int,int) range checks, determinism and bounded output
 * @run main RandomWitupTest
 */

import java.util.Arrays;
import java.util.Random;

public class RandomWitupTest {

    public static void main(String[] args) {
        testValuesWithinRangeAndDeterministic();
        testSingleValueRange();
        testInvalidRangesThrow();
        testExtremeRangeStaysBounded();
    }

    static void testValuesWithinRangeAndDeterministic() {
        int[] a = new Random(42L).ints(-10, 10).limit(1000).toArray();
        int[] b = new Random(42L).ints(-10, 10).limit(1000).toArray();
        if (a.length != 1000) {
            throw new RuntimeException("unexpected length " + a.length);
        }
        for (int v : a) {
            if (v < -10 || v >= 10) {
                throw new RuntimeException("value out of range: " + v);
            }
        }
        if (!Arrays.equals(a, b)) {
            throw new RuntimeException("same seed produced different sequences");
        }
    }

    static void testSingleValueRange() {
        int[] values = new Random(1L).ints(5, 6).limit(50).toArray();
        for (int v : values) {
            if (v != 5) {
                throw new RuntimeException("expected only 5 but got " + v);
            }
        }
    }

    static void testInvalidRangesThrow() {
        expectIllegalArgument(5, 3);
        expectIllegalArgument(7, 7);
        expectIllegalArgument(0, -1);
    }

    static void expectIllegalArgument(int origin, int bound) {
        try {
            new Random(0L).ints(origin, bound);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("no IllegalArgumentException for " + origin + "," + bound);
    }

    static void testExtremeRangeStaysBounded() {
        int[] values = new Random(7L).ints(Integer.MIN_VALUE, Integer.MAX_VALUE).limit(1000).toArray();
        for (int v : values) {
            if (v < Integer.MIN_VALUE || v >= Integer.MAX_VALUE) {
                throw new RuntimeException("extreme value out of range: " + v);
            }
        }
    }
}
