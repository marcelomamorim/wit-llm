/*
 * @test
 * @summary Test IntPipeline.findAny() method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import java.util.stream.IntPipeline;
import java.util.function.Supplier;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testFindAnyOnEmptyStream();
        testFindAnyOnSingleElementStream();
        testFindAnyOnMultipleElementsStream();
    }

    private static void testFindAnyOnEmptyStream() {
        IntStream emptyStream = IntStream.empty();
        OptionalInt result = emptyStream.findAny();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalInt for empty stream");
        }
    }

    private static void testFindAnyOnSingleElementStream() {
        IntStream singleStream = IntStream.of(42);
        OptionalInt result = singleStream.findAny();
        if (!result.isPresent() || result.getAsInt() != 42) {
            throw new AssertionError("Expected OptionalInt with value 42 for single element stream");
        }
    }

    private static void testFindAnyOnMultipleElementsStream() {
        IntStream multiStream = IntStream.of(1, 2, 3, 4, 5);
        OptionalInt result = multiStream.findAny();
        if (!result.isPresent()) {
            throw new AssertionError("Expected non-empty OptionalInt for non-empty stream");
        }
        int val = result.getAsInt();
        if (val < 1 || val > 5) {
            throw new AssertionError("findAny() returned value out of expected range: " + val);
        }
    }
}
