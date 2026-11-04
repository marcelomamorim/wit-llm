/*
 * @test
 * @summary Verifies public LongStream.min() results and the already-consumed stream IllegalStateException via LongPipeline.min()
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        check(LongStream.of(3L, 1L, 2L).min().equals(OptionalLong.of(1L)),
              "min of 3,1,2 should be 1");

        check(LongStream.of(Long.MAX_VALUE, Long.MIN_VALUE, 0L).min()
                        .equals(OptionalLong.of(Long.MIN_VALUE)),
              "min should handle Long.MIN_VALUE");

        check(LongStream.of(-5L).min().equals(OptionalLong.of(-5L)),
              "min of single negative element should be that element");

        check(LongStream.of(7L, 7L, 7L).min().equals(OptionalLong.of(7L)),
              "min of equal elements should be that value");

        check(!LongStream.empty().min().isPresent(),
              "min of empty stream should be empty");

        LongStream consumed = LongStream.of(1L, 2L);
        consumed.min();
        boolean thrown = false;
        try {
            consumed.min();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "reusing a consumed stream should throw IllegalStateException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
