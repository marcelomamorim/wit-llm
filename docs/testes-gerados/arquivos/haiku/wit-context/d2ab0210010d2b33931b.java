/*
 * @test
 * @summary Behavioral tests for IntStream.skip(long) via public API, including negative-argument rejection
 * @run main IntPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testNegativeSkipThrowsIllegalArgumentException();
        testNegativeMinValueMessage();
        testSkipZeroKeepsAllElements();
        testSkipPositiveDropsPrefix();
        testSkipAtLeastSizeYieldsEmpty();
        testSkipHugeValueYieldsEmpty();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeSkipThrowsIllegalArgumentException() {
        try {
            IntStream.of(1, 2, 3).skip(-1);
            throw new RuntimeException("expected IllegalArgumentException for skip(-1)");
        } catch (IllegalArgumentException e) {
            check("-1".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }

    static void testNegativeMinValueMessage() {
        try {
            IntStream.empty().skip(Long.MIN_VALUE);
            throw new RuntimeException("expected IllegalArgumentException for Long.MIN_VALUE");
        } catch (IllegalArgumentException e) {
            check(Long.toString(Long.MIN_VALUE).equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
    }

    static void testSkipZeroKeepsAllElements() {
        int[] result = IntStream.of(1, 2, 3).skip(0).toArray();
        check(Arrays.equals(new int[] {1, 2, 3}, result),
              "skip(0) changed elements: " + Arrays.toString(result));
    }

    static void testSkipPositiveDropsPrefix() {
        int[] result = IntStream.of(1, 2, 3, 4, 5).skip(2).toArray();
        check(Arrays.equals(new int[] {3, 4, 5}, result),
              "skip(2) wrong result: " + Arrays.toString(result));
    }

    static void testSkipAtLeastSizeYieldsEmpty() {
        int[] result = IntStream.of(1, 2, 3).skip(3).toArray();
        check(result.length == 0, "skip(3) should be empty: " + Arrays.toString(result));
        result = IntStream.of(1, 2, 3).skip(10).toArray();
        check(result.length == 0, "skip(10) should be empty: " + Arrays.toString(result));
    }

    static void testSkipHugeValueYieldsEmpty() {
        int[] result = IntStream.of(1, 2, 3).skip(Long.MAX_VALUE).toArray();
        check(result.length == 0, "skip(Long.MAX_VALUE) should be empty: " + Arrays.toString(result));
    }
}
