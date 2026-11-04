/*
 * @test
 * @summary Test DoublePipeline.mapToObj method for null and simple mapping
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.stream.Stream;
import java.util.function.DoubleFunction;
import java.util.Objects;

public class DoublePipelineWitupTest {

    // Minimal concrete subclass to test mapToObj
    static class TestDoublePipeline extends DoublePipeline<Object> {
        TestDoublePipeline() {
            super((Spliterator<Double>) null, 0, false);
        }

        @Override
        public void forEach(java.util.function.DoubleConsumer action) {
            // no-op
        }

        @Override
        public Stream<Object> mapToObj(DoubleFunction<?> mapper) {
            return super.mapToObj(mapper);
        }
    }

    public static void main(String[] args) {
        TestDoublePipeline pipeline = new TestDoublePipeline();

        // Test that passing null to mapToObj throws NullPointerException
        try {
            pipeline.mapToObj(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test that mapToObj returns a Stream with mapped elements
        DoubleFunction<String> mapper = new DoubleFunction<String>() {
            public String apply(double d) {
                return "val:" + d;
            }
        };

        Stream<String> stream = pipeline.mapToObj(mapper);
        if (stream == null) {
            throw new RuntimeException("mapToObj returned null stream");
        }

        // Since the pipeline is empty (null spliterator), the stream should be empty
        if (stream.findAny().isPresent()) {
            throw new RuntimeException("Stream should be empty");
        }
    }
}
