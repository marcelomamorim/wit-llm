/*
 * @test
 * @summary WITUP-derived checks for ThreadLocalRandom.longs(long, long) range validation and bounded output
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testEqualBoundsRejected();
        testReversedBoundsRejected();
        testValidRangeValuesWithinBounds();
        testSingleValueRange();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEqualBoundsRejected() {
        boolean thrown = false;
        try {
            ThreadLocalRandom.current().longs(5L, 5L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException when origin equals bound");
    }

    static void testReversedBoundsRejected() {
        boolean thrown = false;
        try {
            ThreadLocalRandom.current().longs(10L, -10L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException when origin is greater than bound");
    }

    static void testValidRangeValuesWithinBounds() {
        long[] values = ThreadLocalRandom.current().longs(-10L, 10L).limit(1000).toArray();
        check(values.length == 1000, "expected 1000 generated values");
        for (long v : values) {
            check(v >= -10L && v < 10L, "generated value out of range [-10, 10): " + v);
        }
    }

    static void testSingleValueRange() {
        long[] values = ThreadLocalRandom.current().longs(5L, 6L).limit(50).toArray();
        check(values.length == 50, "expected 50 generated values");
        for (long v : values) {
            check(v == 5L, "expected only value 5 for range [5, 6), got " + v);
        }
    }
}
