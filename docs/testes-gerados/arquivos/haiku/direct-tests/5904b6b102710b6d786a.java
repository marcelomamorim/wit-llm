/*
 * @test
 * @summary Verifies LongStream.max() behavior, which is implemented by LongPipeline.max() via reduce(Math::max)
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        check(LongStream.of(3L, 1L, 2L).max(), 3L);
        check(LongStream.of(-5L, -2L, -9L).max(), -2L);
        check(LongStream.of(Long.MIN_VALUE, Long.MAX_VALUE).max(), Long.MAX_VALUE);
        check(LongStream.of(Long.MIN_VALUE).max(), Long.MIN_VALUE);
        check(LongStream.of(7L, 7L, 7L).max(), 7L);
        check(LongStream.rangeClosed(1L, 10000L).parallel().max(), 10000L);
        checkEmpty(LongStream.empty().max());
        checkEmpty(LongStream.empty().parallel().max());
    }

    static void check(OptionalLong actual, long expected) {
        if (!actual.isPresent()) {
            throw new RuntimeException("Expected present value " + expected + " but got empty");
        }
        if (actual.getAsLong() != expected) {
            throw new RuntimeException("Expected " + expected + " but got " + actual.getAsLong());
        }
    }

    static void checkEmpty(OptionalLong actual) {
        if (actual.isPresent()) {
            throw new RuntimeException("Expected empty result but got " + actual.getAsLong());
        }
    }
}
