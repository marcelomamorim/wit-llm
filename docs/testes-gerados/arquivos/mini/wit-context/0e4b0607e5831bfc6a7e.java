/*
 * @test
 * @summary Test DoublePipeline.mapToObj method for null argument and normal usage
 * @run main DoublePipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import java.util.function.DoubleFunction;

public class DoublePipelineWitupTest {

    // Minimal concrete subclass of DoublePipeline to allow testing
    static class TestDoublePipeline extends DoublePipeline<Object> {
        TestDoublePipeline() {
            super((DoubleStream) null, 0, false);
        }

        @Override
        public void forEach(java.util.function.DoubleConsumer action) {
            // no-op
        }
    }

    public static void main(String[] args) {
        TestDoublePipeline pipeline = new TestDoublePipeline();

        // Test null argument triggers NullPointerException as per contract
        try {
            pipeline.mapToObj(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test normal usage with a simple mapper
        DoubleFunction<String> mapper = new DoubleFunction<String>() {
            public String apply(double value) {
                return "val:" + value;
            }
        };

        Stream<String> result = pipeline.mapToObj(mapper);
        if (result == null) {
            throw new AssertionError("mapToObj returned null stream");
        }

        // Since pipeline is minimal and no source, the stream is empty
        if (result.iterator().hasNext()) {
            throw new AssertionError("Expected empty stream from minimal pipeline");
        }
    }
}
