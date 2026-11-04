/*
 * @test
 * @summary Verify ThreadLocalRandom.nextLong(origin, bound) rejects empty or inverted ranges and returns values within [origin, bound)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    static boolean throwsIllegalArgument(long origin, long bound) {
        try {
            ThreadLocalRandom.current().nextLong(origin, bound);
            return false;
        } catch (IllegalArgumentException expected) {
            return true;
        }
    }

    static void testEmptyOrInvertedRangesThrow() {
        check(throwsIllegalArgument(5L, 5L), "nextLong(5,5) should throw IllegalArgumentException");
        check(throwsIllegalArgument(6L, 5L), "nextLong(6,5) should throw IllegalArgumentException");
        check(throwsIllegalArgument(0L, Long.MIN_VALUE), "nextLong(0,MIN_VALUE) should throw IllegalArgumentException");
        check(throwsIllegalArgument(Long.MIN_VALUE, Long.MIN_VALUE), "nextLong(MIN,MIN) should throw IllegalArgumentException");
        check(throwsIllegalArgument(Long.MAX_VALUE, Long.MAX_VALUE), "nextLong(MAX,MAX) should throw IllegalArgumentException");
    }

    static void testSingleValueRange() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = 0; i < 100; i++) {
            long v = rnd.nextLong(0L, 1L);
            check(v == 0L, "nextLong(0,1) must return 0 but got " + v);
        }
        for (int i = 0; i < 100; i++) {
            long v = rnd.nextLong(Long.MAX_VALUE - 1, Long.MAX_VALUE);
            check(v == Long.MAX_VALUE - 1, "nextLong(MAX-1,MAX) must return MAX-1 but got " + v);
        }
    }

    static void testValuesWithinBounds() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = 0; i < 1000; i++) {
            long v = rnd.nextLong(0L, 10L);
            check(v >= 0L && v < 10L, "value out of [0,10): " + v);
        }
        for (int i = 0; i < 1000; i++) {
            long v = rnd.nextLong(-5L, 5L);
            check(v >= -5L && v < 5L, "value out of [-5,5): " + v);
        }
        for (int i = 0; i < 1000; i++) {
            long v = rnd.nextLong(Long.MIN_VALUE, 0L);
            check(v >= Long.MIN_VALUE && v < 0L, "value out of [MIN,0): " + v);
        }
    }

    public static void main(String[] args) {
        testEmptyOrInvertedRangesThrow();
        testSingleValueRange();
        testValuesWithinBounds();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
