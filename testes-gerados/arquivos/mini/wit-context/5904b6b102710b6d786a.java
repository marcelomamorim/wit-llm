/*
 * @test
 * @summary Test LongPipeline.max() for normal and consumed pipeline behavior
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMaxOnEmptyStream();
        testMaxOnNonEmptyStream();
        testMaxOnConsumedStream();
    }

    private static void testMaxOnEmptyStream() {
        LongStream emptyStream = LongStream.empty();
        OptionalLong max = emptyStream.max();
        if (max.isPresent()) {
            throw new AssertionError("max() on empty stream should be empty OptionalLong");
        }
    }

    private static void testMaxOnNonEmptyStream() {
        long[] values = {1L, 3L, 2L, 5L, 4L};
        LongStream stream = LongStream.of(values);
        OptionalLong max = stream.max();
        if (!max.isPresent() || max.getAsLong() != 5L) {
            throw new AssertionError("max() should return the maximum value 5L");
        }
    }

    private static void testMaxOnConsumedStream() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        // Consume the stream
        stream.count();
        try {
            stream.max();
            throw new AssertionError("max() on consumed stream should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
