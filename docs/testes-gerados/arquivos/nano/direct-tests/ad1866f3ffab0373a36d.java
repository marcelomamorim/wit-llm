/*
 * @test
 * @summary Tests for ReferencePipeline.flatMapToInt method.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.*;
import java.util.function.*;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapToIntWithNullMapper();
        testFlatMapToIntWithEmptyStream();
        testFlatMapToIntWithSingleElement();
        testFlatMapToIntWithMultipleElements();
    }

    static void testFlatMapToIntWithNullMapper() {
        try {
            Stream.of(1, 2, 3).flatMapToInt(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFlatMapToIntWithEmptyStream() {
        IntStream result = Stream.<Integer>empty()
            .flatMapToInt(i -> IntStream.of(i * 10));
        if (result.count() != 0) {
            throw new AssertionError("Expected empty stream");
        }
    }

    static void testFlatMapToIntWithSingleElement() {
        IntStream result = Stream.of(5)
            .flatMapToInt(i -> IntStream.of(i, i + 1));
        int[] expected = {5, 6};
        int index = 0;
        for (int val : result.toArray()) {
            if (val != expected[index++]) {
                throw new AssertionError("Unexpected value: " + val);
            }
        }
    }

    static void testFlatMapToIntWithMultipleElements() {
        IntStream result = Stream.of(1, 2, 3)
            .flatMapToInt(i -> IntStream.of(i, i * 10));
        int[] expected = {1, 10, 2, 20, 3, 30};
        int index = 0;
        for (int val : result.toArray()) {
            if (val != expected[index++]) {
                throw new AssertionError("Unexpected value: " + val);
            }
        }
    }
}
