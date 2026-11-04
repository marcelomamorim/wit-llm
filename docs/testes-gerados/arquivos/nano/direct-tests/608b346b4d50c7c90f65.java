/*
 * @test
 * @summary Tests the flatMap method of LongPipeline for correct behavior.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.LongFunction;
import java.util.stream.Stream;
import java.util.Objects;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapWithNullMapper();
        testFlatMapWithEmptyStream();
        testFlatMapWithSingleElement();
        testFlatMapWithMultipleElements();
    }

    static void testFlatMapWithNullMapper() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        try {
            stream.flatMap(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFlatMapWithEmptyStream() {
        LongStream emptyStream = LongStream.empty();
        LongFunction<LongStream> mapper = t -> LongStream.of(t, t + 10);
        LongStream result = emptyStream.flatMap(mapper);
        long[] expected = {};
        long[] actual = result.toArray();
        if (!java.util.Arrays.equals(expected, actual)) {
            throw new AssertionError("Expected empty array, got: " + java.util.Arrays.toString(actual));
        }
    }

    static void testFlatMapWithSingleElement() {
        LongStream stream = LongStream.of(5L);
        LongFunction<LongStream> mapper = t -> LongStream.of(t, t + 1);
        long[] resultArray = stream.flatMap(mapper).toArray();
        long[] expected = {5L, 6L};
        if (!java.util.Arrays.equals(expected, resultArray)) {
            throw new AssertionError("Expected: " + java.util.Arrays.toString(expected) + ", but got: " + java.util.Arrays.toString(resultArray));
        }
    }

    static void testFlatMapWithMultipleElements() {
        LongStream stream = LongStream.of(1L, 2L);
        LongFunction<LongStream> mapper = t -> LongStream.of(t, t + 10);
        long[] resultArray = stream.flatMap(mapper).toArray();
        long[] expected = {1L, 11L, 2L, 12L};
        if (!java.util.Arrays.equals(expected, resultArray)) {
            throw new AssertionError("Expected: " + java.util.Arrays.toString(expected) + ", but got: " + java.util.Arrays.toString(resultArray));
        }
    }
}
