/*
 * @test
 * @summary Test IntPipeline.min() method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testMinOnEmptyStream();
        testMinOnSingleElementStream();
        testMinOnMultipleElementsStream();
    }

    private static void testMinOnEmptyStream() {
        IntStream emptyStream = IntStream.empty();
        OptionalInt result = emptyStream.min();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalInt for min() on empty stream");
        }
    }

    private static void testMinOnSingleElementStream() {
        IntStream singleElementStream = IntStream.of(42);
        OptionalInt result = singleElementStream.min();
        if (!result.isPresent() || result.getAsInt() != 42) {
            throw new AssertionError("Expected OptionalInt with value 42 for min() on single element stream");
        }
    }

    private static void testMinOnMultipleElementsStream() {
        IntStream stream = IntStream.of(5, 3, 9, 1, 7);
        OptionalInt result = stream.min();
        if (!result.isPresent() || result.getAsInt() != 1) {
            throw new AssertionError("Expected OptionalInt with value 1 for min() on multiple elements stream");
        }
    }
}
