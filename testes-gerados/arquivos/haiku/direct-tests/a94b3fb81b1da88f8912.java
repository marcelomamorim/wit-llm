/*
 * @test
 * @summary Verifies ThreadLocalRandom.longs(long) size handling: empty for zero, exact count for positive sizes, and IllegalArgumentException for negative sizes
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testZeroSizeYieldsEmptyStream();
        testPositiveSizeYieldsExactCount();
        testSizeOneYieldsSingleElement();
        testNegativeSizeThrows();
        testMinusOneThrows();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testZeroSizeYieldsEmptyStream() {
        long count = ThreadLocalRandom.current().longs(0L).count();
        check(count == 0L, "longs(0) expected 0 elements but got " + count);
    }

    static void testPositiveSizeYieldsExactCount() {
        long[] values = ThreadLocalRandom.current().longs(5L).toArray();
        check(values.length == 5, "longs(5) expected 5 elements but got " + values.length);

        long count = ThreadLocalRandom.current().longs(100L).count();
        check(count == 100L, "longs(100) expected 100 elements but got " + count);
    }

    static void testSizeOneYieldsSingleElement() {
        LongStream stream = ThreadLocalRandom.current().longs(1L);
        long[] values = stream.toArray();
        check(values.length == 1, "longs(1) expected 1 element but got " + values.length);
    }

    static void testNegativeSizeThrows() {
        boolean thrown = false;
        try {
            ThreadLocalRandom.current().longs(-5L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "longs(-5) should throw IllegalArgumentException");
    }

    static void testMinusOneThrows() {
        boolean thrown = false;
        try {
            ThreadLocalRandom.current().longs(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "longs(-1) should throw IllegalArgumentException");
    }
}
