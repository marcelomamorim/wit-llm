/*
 * @test
 * @summary Verifies LongPipeline.filter behavior through the public LongStream API
 * @run main LongPipelineWitupTest
 */

import java.util.Arrays;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        long[] evens = LongStream.of(1L, 2L, 3L, 4L, 5L)
                .filter(v -> v % 2 == 0)
                .toArray();
        check(Arrays.equals(evens, new long[] {2L, 4L}), "filter should keep only matching elements in order");

        long count = LongStream.of(1L, 2L, 3L)
                .filter(v -> v > 1)
                .count();
        check(count == 2L, "count after filter should reflect filtered elements");

        long[] none = LongStream.of(1L, 2L)
                .filter(v -> false)
                .toArray();
        check(none.length == 0, "filter rejecting all elements should yield empty result");

        LongPredicate greaterThanTen = v -> v > 10L;
        long sum = LongStream.of(5L, 20L)
                .filter(greaterThanTen)
                .sum();
        check(sum == 20L, "sum after filter should only include accepted elements");

        boolean npe = false;
        try {
            LongStream.of(1L).filter(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "filter(null) should throw NullPointerException");

        final int[] calls = {0};
        LongStream lazy = LongStream.of(1L, 2L).filter(v -> {
            calls[0]++;
            return true;
        });
        check(calls[0] == 0, "filter should not evaluate predicate before a terminal operation");
        long[] result = lazy.toArray();
        check(result.length == 2, "all elements should pass a always-true predicate");
        check(calls[0] == 2, "predicate should be evaluated once per element");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
