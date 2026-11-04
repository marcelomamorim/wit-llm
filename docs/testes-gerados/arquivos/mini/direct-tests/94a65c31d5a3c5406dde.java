/*
 * @test
 * @summary Test IntPipeline.max() method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testMaxEmptyStream();
        testMaxSingleElement();
        testMaxMultipleElements();
    }

    private static void testMaxEmptyStream() {
        IntStream empty = IntStream.empty();
        OptionalInt max = empty.max();
        if (max.isPresent()) {
            throw new AssertionError("Expected empty OptionalInt for max of empty stream");
        }
    }

    private static void testMaxSingleElement() {
        IntStream single = IntStream.of(42);
        OptionalInt max = single.max();
        if (!max.isPresent() || max.getAsInt() != 42) {
            throw new AssertionError("Expected max to be 42 for single-element stream");
        }
    }

    private static void testMaxMultipleElements() {
        IntStream stream = IntStream.of(1, 3, 2, 5, 4);
        OptionalInt max = stream.max();
        if (!max.isPresent() || max.getAsInt() != 5) {
            throw new AssertionError("Expected max to be 5 for stream of multiple elements");
        }
    }
}
