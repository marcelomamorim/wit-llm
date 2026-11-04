/*
 * @test
 * @summary Test LongPipeline.findAny() for normal and consumed pipeline states
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.stream.LongStream;
import java.util.stream.LongPipeline;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamSupport;
import java.util.function.Supplier;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testFindAnyNormal();
        testFindAnyConsumed();
    }

    // Helper class to create a LongPipeline from a Spliterator<Long>
    static class TestLongPipeline extends LongPipeline<Long> {
        TestLongPipeline(Spliterator<Long> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        @Override
        public void forEach(LongConsumer action) {
            // Not used in this test
        }
    }

    private static void testFindAnyNormal() {
        // Create a LongStream with known elements
        LongStream stream = LongStream.of(10L, 20L, 30L);
        OptionalLong result = stream.findAny();
        if (!result.isPresent()) {
            throw new AssertionError("findAny() should return a value for non-empty stream");
        }
        long val = result.getAsLong();
        if (val != 10L && val != 20L && val != 30L) {
            throw new AssertionError("findAny() returned unexpected value: " + val);
        }
    }

    private static void testFindAnyConsumed() {
        // Create a LongStream and consume it, then call findAny() to check for IllegalStateException
        LongStream stream = LongStream.of(1L, 2L, 3L);
        stream.count(); // terminal operation consumes the stream
        try {
            stream.findAny();
            throw new AssertionError("Expected IllegalStateException when calling findAny() on consumed stream");
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
