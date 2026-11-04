/*
 * @test
 * @summary Test LongPipeline.max() method behavior
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMaxEmptyStream();
        testMaxSingleElement();
        testMaxMultipleElements();
    }

    private static void testMaxEmptyStream() {
        LongStream empty = LongStream.empty();
        OptionalLong max = empty.max();
        if (max.isPresent()) {
            throw new AssertionError("max() on empty stream should be empty OptionalLong");
        }
    }

    private static void testMaxSingleElement() {
        LongStream single = LongStream.of(42L);
        OptionalLong max = single.max();
        if (!max.isPresent() || max.getAsLong() != 42L) {
            throw new AssertionError("max() on single-element stream should return that element");
        }
    }

    private static void testMaxMultipleElements() {
        LongStream stream = LongStream.of(1L, 3L, 2L, 5L, 4L);
        OptionalLong max = stream.max();
        if (!max.isPresent() || max.getAsLong() != 5L) {
            throw new AssertionError("max() should return the maximum element");
        }
    }
}
