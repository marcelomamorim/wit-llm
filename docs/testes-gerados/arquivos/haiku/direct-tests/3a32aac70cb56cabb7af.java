/*
 * @test
 * @summary Tests SplittableRandom.ints(origin, bound) range validation, bounds, determinism and stream size
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.PrimitiveIterator;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testInvalidRangeThrows();
        testValuesWithinRange();
        testDeterministicWithSameSeed();
        testSingleValueRange();
        testFullIntRange();
        testLimitedStreamCount();
        System.out.println("All SplittableRandom.ints tests passed");
    }

    static void testInvalidRangeThrows() {
        SplittableRandom rnd = new SplittableRandom(1L);
        boolean threw = false;
        try {
            rnd.ints(5, 5);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check(threw, "ints(5,5) should throw IllegalArgumentException");

        threw = false;
        try {
            rnd.ints(10, 3);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check(threw, "ints(10,3) should throw IllegalArgumentException");
    }

    static void testValuesWithinRange() {
        SplittableRandom rnd = new SplittableRandom(42L);
        int[] values = rnd.ints(-10, 10).limit(1000).toArray();
        check(values.length == 1000, "expected 1000 values");
        for (int v : values) {
            check(v >= -10 && v < 10, "value out of range: " + v);
        }
    }

    static void testDeterministicWithSameSeed() {
        int[] a = new SplittableRandom(12345L).ints(0, 1000).limit(200).toArray();
        int[] b = new SplittableRandom(12345L).ints(0, 1000).limit(200).toArray();
        check(a.length == b.length, "length mismatch");
        for (int i = 0; i < a.length; i++) {
            check(a[i] == b[i], "values differ at index " + i);
        }
    }

    static void testSingleValueRange() {
        SplittableRandom rnd = new SplittableRandom(7L);
        int[] values = rnd.ints(7, 8).limit(100).toArray();
        for (int v : values) {
            check(v == 7, "expected only 7 but got " + v);
        }
    }

    static void testFullIntRange() {
        SplittableRandom rnd = new SplittableRandom(99L);
        int[] values = rnd.ints(Integer.MIN_VALUE, Integer.MAX_VALUE).limit(500).toArray();
        check(values.length == 500, "expected 500 values");
        for (int v : values) {
            check(v >= Integer.MIN_VALUE && v < Integer.MAX_VALUE, "value out of full range: " + v);
        }
    }

    static void testLimitedStreamCount() {
        SplittableRandom rnd = new SplittableRandom(2024L);
        long count = rnd.ints(0, 50).limit(321).count();
        check(count == 321, "expected count 321 but got " + count);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
