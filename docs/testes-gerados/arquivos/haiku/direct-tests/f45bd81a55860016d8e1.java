/*
 * @test
 * @summary Behavioral tests for SplittableRandom.nextLong(long bound)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNonPositiveBoundThrows();
        testBoundOneAlwaysZero();
        testRangeAndDeterminism();
        testPowerOfTwoBound();
        testMaxBound();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNonPositiveBoundThrows() {
        long[] badBounds = {0L, -1L, Long.MIN_VALUE};
        for (long bound : badBounds) {
            SplittableRandom r = new SplittableRandom(1L);
            try {
                r.nextLong(bound);
            } catch (IllegalArgumentException expected) {
                continue;
            }
            throw new RuntimeException("No IllegalArgumentException for bound " + bound);
        }
    }

    static void testBoundOneAlwaysZero() {
        SplittableRandom r = new SplittableRandom(7L);
        for (int i = 0; i < 100; i++) {
            check(r.nextLong(1L) == 0L, "nextLong(1) must return 0");
        }
    }

    static void testRangeAndDeterminism() {
        SplittableRandom a = new SplittableRandom(12345L);
        SplittableRandom b = new SplittableRandom(12345L);
        for (int i = 0; i < 1000; i++) {
            long bound = 1000L + i;
            long x = a.nextLong(bound);
            long y = b.nextLong(bound);
            check(x >= 0L && x < bound, "value out of range: " + x + " for bound " + bound);
            check(x == y, "same seed must produce same sequence at step " + i);
        }
    }

    static void testPowerOfTwoBound() {
        long bound = 1L << 40;
        SplittableRandom r = new SplittableRandom(99L);
        for (int i = 0; i < 500; i++) {
            long x = r.nextLong(bound);
            check(x >= 0L && x < bound, "power-of-two value out of range: " + x);
        }
    }

    static void testMaxBound() {
        SplittableRandom r = new SplittableRandom(2024L);
        for (int i = 0; i < 500; i++) {
            long x = r.nextLong(Long.MAX_VALUE);
            check(x >= 0L && x < Long.MAX_VALUE, "value out of range for MAX_VALUE bound: " + x);
        }
    }
}
