/*
 * @test
 * @summary Verifies IntPipeline.limit behavior: negative sizes throw IllegalArgumentException with the size as message, and valid sizes truncate the stream.
 * @run main IntPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testNegativeLimitThrowsWithSizeMessage();
        testNegativeMinLongLimitThrowsWithSizeMessage();
        testLimitZeroIsEmpty();
        testLimitTruncates();
        testLimitLargerThanSizeKeepsAllElements();
        testLimitOnInfiniteStreamIsLazyAndFinite();
        testLimitLongMaxValueKeepsAllElements();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNegativeLimitThrowsWithSizeMessage() {
        try {
            IntStream.of(1, 2, 3).limit(-1L);
            throw new AssertionError("expected IllegalArgumentException for limit(-1)");
        } catch (IllegalArgumentException e) {
            check("-1".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
    }

    static void testNegativeMinLongLimitThrowsWithSizeMessage() {
        try {
            IntStream.of(1).limit(Long.MIN_VALUE);
            throw new AssertionError("expected IllegalArgumentException for Long.MIN_VALUE");
        } catch (IllegalArgumentException e) {
            check(Long.toString(Long.MIN_VALUE).equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
    }

    static void testLimitZeroIsEmpty() {
        int[] result = IntStream.of(1, 2, 3).limit(0L).toArray();
        check(result.length == 0, "limit(0) should be empty, got " + Arrays.toString(result));
    }

    static void testLimitTruncates() {
        int[] result = IntStream.of(1, 2, 3, 4).limit(2L).toArray();
        check(Arrays.equals(result, new int[] {1, 2}),
              "expected [1, 2], got " + Arrays.toString(result));
    }

    static void testLimitLargerThanSizeKeepsAllElements() {
        int[] result = IntStream.of(5, 6, 7).limit(10L).toArray();
        check(Arrays.equals(result, new int[] {5, 6, 7}),
              "expected [5, 6, 7], got " + Arrays.toString(result));
    }

    static void testLimitOnInfiniteStreamIsLazyAndFinite() {
        int[] result = IntStream.iterate(0, i -> i + 1).limit(3L).toArray();
        check(Arrays.equals(result, new int[] {0, 1, 2}),
              "expected [0, 1, 2], got " + Arrays.toString(result));
    }

    static void testLimitLongMaxValueKeepsAllElements() {
        int[] result = IntStream.of(1, 2, 3).limit(Long.MAX_VALUE).toArray();
        check(Arrays.equals(result, new int[] {1, 2, 3}),
              "expected [1, 2, 3], got " + Arrays.toString(result));
    }
}
