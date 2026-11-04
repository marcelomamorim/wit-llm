/*
 * @test
 * @summary Behavioral tests for LongStream.flatMap via public API (LongPipeline.flatMap)
 * @run main LongPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testFlatMapExpandsElements();
        testNullInnerStreamIgnored();
        testEmptyInnerStreams();
        testShortCircuitWithLimit();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullMapperThrowsNPE() {
        boolean thrown = false;
        try {
            LongStream.of(1L).flatMap(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "flatMap(null) should throw NullPointerException");
    }

    static void testFlatMapExpandsElements() {
        long[] result = LongStream.of(1L, 2L)
                .flatMap(x -> LongStream.of(x, x * 10))
                .toArray();
        check(Arrays.equals(result, new long[]{1L, 10L, 2L, 20L}),
              "flatMap should expand each element in order");
    }

    static void testNullInnerStreamIgnored() {
        long[] result = LongStream.of(1L, 2L, 3L)
                .flatMap(x -> x == 2L ? null : LongStream.of(x))
                .toArray();
        check(Arrays.equals(result, new long[]{1L, 3L}),
              "null inner stream should contribute no elements");
    }

    static void testEmptyInnerStreams() {
        long count = LongStream.of(1L, 2L, 3L)
                .flatMap(x -> LongStream.empty())
                .count();
        check(count == 0L, "empty inner streams should produce no elements");
    }

    static void testShortCircuitWithLimit() {
        long[] result = LongStream.of(1L, 2L, 3L)
                .flatMap(x -> LongStream.of(x, x))
                .limit(3)
                .toArray();
        check(Arrays.equals(result, new long[]{1L, 1L, 2L}),
              "limit after flatMap should yield the first three elements");
    }
}
