/*
 * @test
 * @summary Verify LongPipeline.min() returns the smallest element or an empty OptionalLong
 * @run main LongPipelineWitupTest
 */
import java.util.OptionalLong;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        check(LongStream.of(3L, 1L, 2L).min(), OptionalLong.of(1L), "basic minimum");
        check(LongStream.of(5L).min(), OptionalLong.of(5L), "single element");
        check(LongStream.empty().min(), OptionalLong.empty(), "empty stream");
        check(LongStream.of(-1L, -10L, 0L).min(), OptionalLong.of(-10L), "negative values");
        check(LongStream.of(Long.MAX_VALUE, Long.MIN_VALUE).min(), OptionalLong.of(Long.MIN_VALUE), "extreme values");
        check(LongStream.of(Long.MAX_VALUE).min(), OptionalLong.of(Long.MAX_VALUE), "single max value");
        check(LongStream.of(2L, 2L, 2L).min(), OptionalLong.of(2L), "duplicates");
        check(LongStream.rangeClosed(1L, 10L).filter(x -> x > 4L).min(), OptionalLong.of(5L), "after filter");
        check(LongStream.rangeClosed(1L, 10L).filter(x -> x > 100L).min(), OptionalLong.empty(), "filter removes all");

        if (LongStream.empty().min().isPresent()) {
            throw new RuntimeException("empty stream min should not be present");
        }
    }

    static void check(OptionalLong actual, OptionalLong expected, String name) {
        if (!actual.equals(expected)) {
            throw new RuntimeException(name + ": expected " + expected + " but got " + actual);
        }
    }
}
