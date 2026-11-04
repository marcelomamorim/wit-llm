/*
 * @test
 * @summary Verifies ReferencePipeline.mapToLong maps elements, rejects a null mapper, and is lazy
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapsElements();
        testNullMapperThrowsNPE();
        testLazyEvaluation();
        testEmptyStream();
    }

    static void testMapsElements() {
        long[] result = Stream.of("a", "bb", "ccc")
                .mapToLong(s -> s.length())
                .toArray();
        check(Arrays.equals(result, new long[]{1L, 2L, 3L}),
              "mapToLong should apply mapper to each element: " + Arrays.toString(result));
    }

    static void testNullMapperThrowsNPE() {
        boolean thrown = false;
        try {
            Stream.of("a").mapToLong(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "mapToLong(null) should throw NullPointerException");
    }

    static void testLazyEvaluation() {
        int[] calls = {0};
        LongStream ls = Stream.of("a", "b")
                .mapToLong(s -> {
                    calls[0]++;
                    return 1L;
                });
        check(calls[0] == 0, "mapper should not be invoked before a terminal operation");
        long sum = ls.sum();
        check(calls[0] == 2, "mapper should be invoked once per element, was " + calls[0]);
        check(sum == 2L, "sum should be 2, was " + sum);
    }

    static void testEmptyStream() {
        long count = Stream.<String>empty()
                .mapToLong(s -> s.length())
                .count();
        check(count == 0L, "empty stream mapped to LongStream should have count 0, was " + count);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
