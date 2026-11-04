/*
 * @test
 * @summary Test LongPipeline.findAny() method behavior
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testFindAnyOnEmptyStream();
        testFindAnyOnSingleElementStream();
        testFindAnyOnMultipleElementsStream();
    }

    private static void testFindAnyOnEmptyStream() {
        LongStream emptyStream = LongStream.empty();
        OptionalLong result = emptyStream.findAny();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalLong for empty stream");
        }
    }

    private static void testFindAnyOnSingleElementStream() {
        LongStream singleStream = LongStream.of(42L);
        OptionalLong result = singleStream.findAny();
        if (!result.isPresent() || result.getAsLong() != 42L) {
            throw new AssertionError("Expected OptionalLong with value 42 for single element stream");
        }
    }

    private static void testFindAnyOnMultipleElementsStream() {
        LongStream multiStream = LongStream.of(10L, 20L, 30L);
        OptionalLong result = multiStream.findAny();
        if (!result.isPresent()) {
            throw new AssertionError("Expected non-empty OptionalLong for multi-element stream");
        }
        long val = result.getAsLong();
        if (val != 10L && val != 20L && val != 30L) {
            throw new AssertionError("findAny returned unexpected value: " + val);
        }
    }
}
