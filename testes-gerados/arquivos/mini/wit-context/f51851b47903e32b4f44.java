/*
 * @test
 * @summary Test LongPipeline.min() method behavior including empty stream and single element stream
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMinEmptyStream();
        testMinSingleElementStream();
        testMinMultipleElementsStream();
    }

    private static void testMinEmptyStream() {
        LongStream emptyStream = LongStream.empty();
        OptionalLong result = emptyStream.min();
        if (result.isPresent()) {
            throw new AssertionError("Expected OptionalLong.empty() for min() on empty stream");
        }
    }

    private static void testMinSingleElementStream() {
        LongStream singleElementStream = LongStream.of(42L);
        OptionalLong result = singleElementStream.min();
        if (!result.isPresent() || result.getAsLong() != 42L) {
            throw new AssertionError("Expected OptionalLong.of(42) for min() on single element stream");
        }
    }

    private static void testMinMultipleElementsStream() {
        LongStream stream = LongStream.of(10L, 5L, 20L, 5L);
        OptionalLong result = stream.min();
        if (!result.isPresent() || result.getAsLong() != 5L) {
            throw new AssertionError("Expected OptionalLong.of(5) for min() on multiple elements stream");
        }
    }

}
