/*
 * @test
 * @summary Test LongPipeline.mapToObj(LongFunction) for null and simple mapping
 * @run main LongPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.LongStream;
import java.util.function.LongFunction;

public class LongPipelineWitupTest {

    static class DummyLongPipeline extends java.util.stream.LongPipeline<Long> {
        DummyLongPipeline() {
            super((Spliterator<Long>) null, 0, false);
        }

        // Expose mapToObj for testing
        public <U> Stream<U> testMapToObj(LongFunction<? extends U> mapper) {
            return mapToObj(mapper);
        }
    }

    public static void main(String[] args) {
        DummyLongPipeline pipeline = new DummyLongPipeline();

        // Test null mapper throws NullPointerException
        try {
            pipeline.testMapToObj(null);
            throw new Error("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
        }

        // Test simple mapping from long to String
        Stream<String> stream = pipeline.testMapToObj(new LongFunction<String>() {
            public String apply(long value) {
                return "val:" + value;
            }
        });

        // The returned stream should not be null
        if (stream == null) {
            throw new Error("mapToObj returned null stream");
        }

        // Since pipeline is dummy with null spliterator, stream is empty
        if (stream.findAny().isPresent()) {
            throw new Error("Expected empty stream from dummy pipeline");
        }
    }
}
