/*
 * @test
 * @summary Test LongPipeline.min() method behavior
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        testMinOnEmptyStream();
        testMinOnSingleElementStream();
        testMinOnMultipleElementsStream();
    }

    private static void testMinOnEmptyStream() {
        LongStream empty = LongStream.empty();
        OptionalLong result = empty.min();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalLong for min() on empty stream");
        }
    }

    private static void testMinOnSingleElementStream() {
        LongStream single = LongStream.of(42L);
        OptionalLong result = single.min();
        if (!result.isPresent() || result.getAsLong() != 42L) {
            throw new AssertionError("Expected OptionalLong with value 42 for min() on single-element stream");
        }
    }

    private static void testMinOnMultipleElementsStream() {
        LongStream stream = LongStream.of(10L, 5L, 20L, -3L, 7L);
        OptionalLong result = stream.min();
        if (!result.isPresent() || result.getAsLong() != -3L) {
            throw new AssertionError("Expected OptionalLong with value -3 for min() on multiple-element stream");
        }
    }
}
