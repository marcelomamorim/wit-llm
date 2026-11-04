/*
 * @test
 * @summary Verify LongStream.limit rejects negative sizes with IllegalArgumentException carrying the value, and behaves correctly for valid sizes
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testNegativeOneThrows();
        testNegativeMinValueThrows();
        testNegativeFiveMessage();
        testZeroLimitIsEmpty();
        testLimitKeepsPrefix();
        testLimitLargerThanSource();
        testLimitOnRangeStream();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeOneThrows() {
        try {
            LongStream.of(1L, 2L, 3L).limit(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for limit(-1)");
        } catch (IllegalArgumentException e) {
            check("-1".equals(e.getMessage()),
                  "Unexpected message: " + e.getMessage());
        }
    }

    static void testNegativeMinValueThrows() {
        try {
            LongStream.of(1L).limit(Long.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for Long.MIN_VALUE");
        } catch (IllegalArgumentException e) {
            check(Long.toString(Long.MIN_VALUE).equals(e.getMessage()),
                  "Unexpected message: " + e.getMessage());
        }
    }

    static void testNegativeFiveMessage() {
        try {
            LongStream.range(0L, 10L).limit(-5L);
            throw new RuntimeException("Expected IllegalArgumentException for limit(-5)");
        } catch (IllegalArgumentException e) {
            check("-5".equals(e.getMessage()),
                  "Unexpected message: " + e.getMessage());
        }
    }

    static void testZeroLimitIsEmpty() {
        long count = LongStream.of(1L, 2L, 3L).limit(0L).count();
        check(count == 0L, "limit(0) should yield no elements, got " + count);
    }

    static void testLimitKeepsPrefix() {
        long[] result = LongStream.of(10L, 20L, 30L, 40L).limit(2L).toArray();
        check(result.length == 2, "Expected 2 elements, got " + result.length);
        check(result[0] == 10L && result[1] == 20L,
              "Unexpected prefix elements");
    }

    static void testLimitLargerThanSource() {
        long[] result = LongStream.of(1L, 2L).limit(100L).toArray();
        check(result.length == 2, "Expected 2 elements, got " + result.length);
    }

    static void testLimitOnRangeStream() {
        long sum = LongStream.range(0L, 1000L).limit(4L).sum();
        check(sum == 0L + 1L + 2L + 3L, "Unexpected sum: " + sum);
    }
}
