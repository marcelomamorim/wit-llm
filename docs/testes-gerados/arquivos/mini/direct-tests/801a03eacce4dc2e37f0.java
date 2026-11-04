/*
 * @test
 * @summary Test LongPipeline.findFirst() method behavior
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testFindFirstOnEmptyStream();
        testFindFirstOnSingleElementStream();
        testFindFirstOnMultipleElementsStream();
    }

    private static void testFindFirstOnEmptyStream() {
        LongStream emptyStream = LongStream.empty();
        OptionalLong result = emptyStream.findFirst();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalLong for empty stream");
        }
    }

    private static void testFindFirstOnSingleElementStream() {
        LongStream singleElementStream = LongStream.of(42L);
        OptionalLong result = singleElementStream.findFirst();
        if (!result.isPresent() || result.getAsLong() != 42L) {
            throw new AssertionError("Expected OptionalLong with value 42 for single element stream");
        }
    }

    private static void testFindFirstOnMultipleElementsStream() {
        LongStream multiElementStream = LongStream.of(10L, 20L, 30L);
        OptionalLong result = multiElementStream.findFirst();
        if (!result.isPresent() || result.getAsLong() != 10L) {
            throw new AssertionError("Expected OptionalLong with value 10 for multiple element stream");
        }
    }
}
