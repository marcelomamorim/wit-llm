/*
 * @test
 * @summary Verifies LongStream.skip behavior for zero, positive, out-of-range, negative and parallel inputs
 * @run main LongPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkArray(long[] actual, long[] expected, String message) {
        if (!Arrays.equals(actual, expected)) {
            throw new RuntimeException(message + ": expected " + Arrays.toString(expected)
                    + " but was " + Arrays.toString(actual));
        }
    }

    static void testSkipZeroKeepsAllElements() {
        checkArray(LongStream.of(1L, 2L, 3L, 4L, 5L).skip(0).toArray(),
                   new long[] {1L, 2L, 3L, 4L, 5L},
                   "skip(0) should keep all elements");
    }

    static void testSkipPositiveDropsPrefix() {
        checkArray(LongStream.of(1L, 2L, 3L, 4L, 5L).skip(2).toArray(),
                   new long[] {3L, 4L, 5L},
                   "skip(2) should drop first two elements");
        checkArray(LongStream.of(1L, 2L, 3L).skip(1).toArray(),
                   new long[] {2L, 3L},
                   "skip(1) should drop first element");
    }

    static void testSkipAllOrMoreYieldsEmpty() {
        checkArray(LongStream.of(1L, 2L, 3L).skip(3).toArray(),
                   new long[0],
                   "skip(size) should yield empty stream");
        checkArray(LongStream.of(1L, 2L, 3L).skip(10L).toArray(),
                   new long[0],
                   "skip(greater than size) should yield empty stream");
    }

    static void testSkipOnEmptyStream() {
        checkArray(LongStream.empty().skip(0).toArray(),
                   new long[0],
                   "skip(0) on empty stream");
        checkArray(LongStream.empty().skip(5).toArray(),
                   new long[0],
                   "skip(5) on empty stream");
    }

    static void testSkipLargeValueDoesNotOverflowCount() {
        check(LongStream.range(0L, 100L).skip(Long.MAX_VALUE).count() == 0L,
              "skip(Long.MAX_VALUE) should yield no elements");
    }

    static void testSkipParallelCount() {
        long count = LongStream.range(0L, 100L).parallel().skip(50L).count();
        check(count == 50L, "parallel skip(50) on 0..99 should count 50 but was " + count);
    }

    static void testNegativeSkipThrowsWithValueMessage() {
        assertIllegalArgument(-1L, "-1");
        assertIllegalArgument(Long.MIN_VALUE, Long.toString(Long.MIN_VALUE));
    }

    static void assertIllegalArgument(long n, String expectedMessage) {
        try {
            LongStream.of(1L, 2L).skip(n);
        } catch (IllegalArgumentException e) {
            check(expectedMessage.equals(e.getMessage()),
                  "skip(" + n + ") message should be " + expectedMessage
                  + " but was " + e.getMessage());
            return;
        }
        throw new RuntimeException("skip(" + n + ") should throw IllegalArgumentException");
    }

    public static void main(String[] args) {
        testSkipZeroKeepsAllElements();
        testSkipPositiveDropsPrefix();
        testSkipAllOrMoreYieldsEmpty();
        testSkipOnEmptyStream();
        testSkipLargeValueDoesNotOverflowCount();
        testSkipParallelCount();
        testNegativeSkipThrowsWithValueMessage();
    }
}
