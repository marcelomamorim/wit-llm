/*
 * @test
 * @summary Behavioral tests for LongPipeline.sum() through the public LongStream API
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        check(LongStream.empty().sum() == 0L, "empty stream sum must be 0");
        check(LongStream.of(5L).sum() == 5L, "single element sum");
        check(LongStream.of(1L, 2L, 3L, 4L).sum() == 10L, "small sequence sum");
        check(LongStream.of(-3L, 10L, -7L).sum() == 0L, "mixed sign sum");
        check(LongStream.rangeClosed(1, 1000).sum() == 500500L, "range sum");
        check(LongStream.rangeClosed(1, 1000).parallel().sum() == 500500L, "parallel sum");
        check(LongStream.of(1L, 2L, 3L, 4L, 5L).filter(x -> x % 2 == 1).sum() == 9L, "filtered sum");
        check(LongStream.of(1L, 2L).map(x -> x * 10L).sum() == 30L, "mapped sum");
        check(LongStream.of(Long.MAX_VALUE, 1L).sum() == Long.MIN_VALUE, "overflow wraps as long addition");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
