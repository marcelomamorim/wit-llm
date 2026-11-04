/*
 * @test
 * @summary Test IntPipeline.mapToObj behavior including null argument check
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.Stream;
import java.util.function.IntFunction;
import java.util.Objects;

public class IntPipelineWitupTest {

    // Minimal subclass to allow instantiation of abstract IntPipeline
    static class TestIntPipeline extends IntPipeline<Integer> {
        TestIntPipeline() {
            super((Spliterator<Integer>) null, 0, false);
        }

        @Override
        public void forEach(IntConsumer action) {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        IntPipeline<Integer> pipeline = new TestIntPipeline();

        // Test 1: mapToObj with null argument should throw NullPointerException
        try {
            pipeline.mapToObj(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: mapToObj with a valid mapper returns a non-null Stream
        IntFunction<String> mapper = new IntFunction<String>() {
            public String apply(int value) {
                return "val" + value;
            }
        };

        Stream<String> result = pipeline.mapToObj(mapper);
        if (result == null) {
            throw new AssertionError("mapToObj returned null Stream");
        }

        // We cannot test more deeply without full pipeline setup, but this
        // confirms basic contract: non-null input yields non-null output.
    }
}
