/*
 * @test
 * @summary Behavioral checks for LongPipeline.flatMap through the public LongStream API
 * @run main LongPipelineWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testNullMapperThrowsNPE() {
        try {
            LongStream.of(1L).flatMap(null);
            check(false, "flatMap(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testBasicExpansion() {
        long[] result = LongStream.of(1L, 2L, 3L)
                .flatMap(x -> LongStream.of(x, x * 10))
                .toArray();
        long[] expected = {1L, 10L, 2L, 20L, 3L, 30L};
        check(Arrays.equals(expected, result), "basic expansion order/content");
    }

    static void testEmptyInnerStreams() {
        long[] result = LongStream.of(1L, 2L, 3L)
                .flatMap(x -> LongStream.empty())
                .toArray();
        check(result.length == 0, "empty inner streams should yield no elements");
    }

    static void testNullResultFromMapperIsSkipped() {
        long[] result = LongStream.of(1L, 2L, 3L)
                .flatMap(x -> x == 2L ? null : LongStream.of(x))
                .toArray();
        long[] expected = {1L, 3L};
        check(Arrays.equals(expected, result), "null inner stream should be skipped");
    }

    static void testEmptySourceYieldsEmpty() {
        long[] result = LongStream.empty()
                .flatMap(x -> LongStream.of(x, x))
                .toArray();
        check(result.length == 0, "empty source should yield no elements");
    }

    static void testShortCircuitWithLimit() {
        long[] result = LongStream.of(1L, 2L, 3L)
                .flatMap(x -> LongStream.of(x, x + 100L))
                .limit(3)
                .toArray();
        long[] expected = {1L, 101L, 2L};
        check(Arrays.equals(expected, result), "limit after flatMap should short-circuit correctly");
    }

    static void testInnerStreamIsClosed() {
        AtomicBoolean closed = new AtomicBoolean(false);
        long count = LongStream.of(7L)
                .flatMap(x -> LongStream.of(x).onClose(() -> closed.set(true)))
                .count();
        check(count == 1L, "flatMap should emit the inner element");
        check(closed.get(), "inner stream should be closed after consumption");
    }

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testBasicExpansion();
        testEmptyInnerStreams();
        testNullResultFromMapperIsSkipped();
        testEmptySourceYieldsEmpty();
        testShortCircuitWithLimit();
        testInnerStreamIsClosed();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
