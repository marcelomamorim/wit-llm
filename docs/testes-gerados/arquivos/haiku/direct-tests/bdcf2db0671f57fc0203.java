/*
 * @test
 * @summary Tests ThreadLocalRandom.longs(origin, bound) range validation and bounded output values
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testValuesWithinRange();
        testSingleValueRange();
        testNegativeRange();
        testOriginGreaterThanBoundThrows();
        testOriginEqualToBoundThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValuesWithinRange() {
        long[] values = ThreadLocalRandom.current().longs(5L, 10L).limit(1000).toArray();
        check(values.length == 1000, "expected 1000 values but got " + values.length);
        for (long v : values) {
            check(v >= 5L && v < 10L, "value out of range [5,10): " + v);
        }
    }

    static void testSingleValueRange() {
        long[] values = ThreadLocalRandom.current().longs(7L, 8L).limit(200).toArray();
        check(values.length == 200, "expected 200 values but got " + values.length);
        for (long v : values) {
            check(v == 7L, "expected only 7 in range [7,8) but got " + v);
        }
    }

    static void testNegativeRange() {
        long[] values = ThreadLocalRandom.current().longs(-100L, 100L).limit(1000).toArray();
        check(values.length == 1000, "expected 1000 values but got " + values.length);
        for (long v : values) {
            check(v >= -100L && v < 100L, "value out of range [-100,100): " + v);
        }
    }

    static void testOriginGreaterThanBoundThrows() {
        boolean thrown = false;
        try {
            ThreadLocalRandom.current().longs(10L, 5L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException when origin > bound");
    }

    static void testOriginEqualToBoundThrows() {
        boolean thrown = false;
        try {
            ThreadLocalRandom.current().longs(5L, 5L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException when origin == bound");
    }
}
