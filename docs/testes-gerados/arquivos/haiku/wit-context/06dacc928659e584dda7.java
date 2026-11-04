/*
 * @test
 * @summary Verifies LongStream.skip argument validation and observable skipping behavior via public API
 * @run main LongPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testNegativeSkipThrowsIllegalArgumentException();
        testZeroSkipKeepsAllElements();
        testPositiveSkipRemovesPrefix();
        testSkipEqualToSizeYieldsEmpty();
        testSkipBeyondSizeYieldsEmpty();
        testSkipOnEmptyStream();
    }

    static void testNegativeSkipThrowsIllegalArgumentException() {
        try {
            LongStream.of(1L, 2L).skip(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for skip(-1)");
        } catch (IllegalArgumentException e) {
            if (!"-1".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testZeroSkipKeepsAllElements() {
        long[] result = LongStream.of(1L, 2L, 3L).skip(0L).toArray();
        check(new long[] {1L, 2L, 3L}, result, "skip(0)");
    }

    static void testPositiveSkipRemovesPrefix() {
        long[] result = LongStream.of(1L, 2L, 3L, 4L, 5L).skip(2L).toArray();
        check(new long[] {3L, 4L, 5L}, result, "skip(2)");
    }

    static void testSkipEqualToSizeYieldsEmpty() {
        long[] result = LongStream.of(1L, 2L, 3L).skip(3L).toArray();
        check(new long[0], result, "skip(size)");
    }

    static void testSkipBeyondSizeYieldsEmpty() {
        long[] result = LongStream.of(1L, 2L, 3L).skip(10L).toArray();
        check(new long[0], result, "skip(beyond size)");
    }

    static void testSkipOnEmptyStream() {
        long[] result = LongStream.empty().skip(1L).toArray();
        check(new long[0], result, "skip on empty");
    }

    static void check(long[] expected, long[] actual, String label) {
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException(label + ": expected " + Arrays.toString(expected)
                    + " but was " + Arrays.toString(actual));
        }
    }
}
