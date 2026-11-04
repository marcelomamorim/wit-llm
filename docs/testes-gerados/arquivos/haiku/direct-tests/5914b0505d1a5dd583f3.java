/*
 * @test
 * @summary Witup-style tests for SplittableRandom.nextLong(long origin, long bound)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testEqualBoundsRejected();
        testReversedBoundsRejected();
        testResultsWithinRange();
        testFullRangeWithinBounds();
        testSingleValueRange();
        testDeterministicSeed();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEqualBoundsRejected() {
        SplittableRandom r = new SplittableRandom(1L);
        try {
            r.nextLong(5L, 5L);
            throw new RuntimeException("expected IllegalArgumentException for origin == bound");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testReversedBoundsRejected() {
        SplittableRandom r = new SplittableRandom(2L);
        try {
            r.nextLong(10L, -10L);
            throw new RuntimeException("expected IllegalArgumentException for origin > bound");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testResultsWithinRange() {
        SplittableRandom r = new SplittableRandom(3L);
        long origin = -5L;
        long bound = 5L;
        for (int i = 0; i < 1000; i++) {
            long v = r.nextLong(origin, bound);
            check(v >= origin && v < bound, "value out of range: " + v);
        }
    }

    static void testFullRangeWithinBounds() {
        SplittableRandom r = new SplittableRandom(4L);
        long origin = Long.MIN_VALUE;
        long bound = Long.MAX_VALUE;
        for (int i = 0; i < 1000; i++) {
            long v = r.nextLong(origin, bound);
            check(v >= origin && v < bound, "value out of full range: " + v);
        }
    }

    static void testSingleValueRange() {
        SplittableRandom r = new SplittableRandom(5L);
        for (int i = 0; i < 100; i++) {
            check(r.nextLong(0L, 1L) == 0L, "expected 0 for range [0,1)");
        }
        for (int i = 0; i < 100; i++) {
            check(r.nextLong(Long.MAX_VALUE - 1, Long.MAX_VALUE) == Long.MAX_VALUE - 1,
                  "expected MAX_VALUE-1 for range [MAX-1,MAX)");
        }
    }

    static void testDeterministicSeed() {
        SplittableRandom a = new SplittableRandom(42L);
        SplittableRandom b = new SplittableRandom(42L);
        for (int i = 0; i < 200; i++) {
            long va = a.nextLong(0L, 100L);
            long vb = b.nextLong(0L, 100L);
            check(va == vb, "same seed produced different values at step " + i);
        }
    }
}
