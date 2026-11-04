/*
 * @test
 * @summary Test IntPipeline.flatMap behavior with various IntFunction mappers
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntFunction;
import java.util.Objects;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapWithValidMapper();
        testFlatMapWithMapperReturningNull();
        testFlatMapWithMapperReturningEmptyStream();
        testFlatMapWithMapperReturningMultipleElements();
    }

    static void testFlatMapWithValidMapper() {
        IntStream base = IntStream.of(1, 2, 3);
        IntFunction<IntStream> mapper = new IntFunction<IntStream>() {
            public IntStream apply(int value) {
                return IntStream.of(value, value * 10);
            }
        };
        IntStream result = base.flatMap(mapper);
        int[] expected = {1, 10, 2, 20, 3, 30};
        int[] actual = result.toArray();
        if (actual.length != expected.length) {
            throw new AssertionError("Length mismatch: expected " + expected.length + " but got " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Element mismatch at index " + i + ": expected " + expected[i] + " but got " + actual[i]);
            }
        }
    }

    static void testFlatMapWithMapperReturningNull() {
        IntStream base = IntStream.of(1, 2);
        IntFunction<IntStream> mapper = new IntFunction<IntStream>() {
            public IntStream apply(int value) {
                return null;
            }
        };
        IntStream result = base.flatMap(mapper);
        // The flatMap implementation skips null streams, so result should be empty
        int[] actual = result.toArray();
        if (actual.length != 0) {
            throw new AssertionError("Expected empty stream when mapper returns null, but got length " + actual.length);
        }
    }

    static void testFlatMapWithMapperReturningEmptyStream() {
        IntStream base = IntStream.of(5, 6);
        IntFunction<IntStream> mapper = new IntFunction<IntStream>() {
            public IntStream apply(int value) {
                return IntStream.empty();
            }
        };
        IntStream result = base.flatMap(mapper);
        int[] actual = result.toArray();
        if (actual.length != 0) {
            throw new AssertionError("Expected empty stream when mapper returns empty streams, but got length " + actual.length);
        }
    }

    static void testFlatMapWithMapperReturningMultipleElements() {
        IntStream base = IntStream.of(0);
        IntFunction<IntStream> mapper = new IntFunction<IntStream>() {
            public IntStream apply(int value) {
                return IntStream.of(1, 2, 3);
            }
        };
        IntStream result = base.flatMap(mapper);
        int[] expected = {1, 2, 3};
        int[] actual = result.toArray();
        if (actual.length != expected.length) {
            throw new AssertionError("Length mismatch: expected " + expected.length + " but got " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Element mismatch at index " + i + ": expected " + expected[i] + " but got " + actual[i]);
            }
        }
    }

}