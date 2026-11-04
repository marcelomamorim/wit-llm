/*
 * @test
 * @summary Test ReferencePipeline.toArray(IntFunction) for normal and illegal state scenarios
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.IntFunction;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testToArrayNormal();
        testToArrayIllegalStateException();
    }

    // Helper method to create a ReferencePipeline from an array source
    static ReferencePipeline<String, String> createPipeline(final String[] data) {
        Supplier<Spliterator<String>> supplier = new Supplier<Spliterator<String>>() {
            public Spliterator<String> get() {
                return Spliterators.spliterator(data, 0);
            }
        };
        // sourceFlags = 0, parallel = false
        return new ReferencePipeline<String, String>(supplier, 0, false) {
            // Implement abstract methods minimally to allow instantiation
            @Override
            public void forEach(java.util.function.Consumer<? super String> action) {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Test normal toArray usage with a valid generator
    static void testToArrayNormal() {
        String[] input = {"a", "b", "c"};
        ReferencePipeline<String, String> pipeline = createPipeline(input);

        IntFunction<String[]> generator = new IntFunction<String[]>() {
            public String[] apply(int size) {
                return new String[size];
            }
        };

        String[] result = pipeline.toArray(generator);

        if (result == null) {
            throw new AssertionError("toArray returned null");
        }
        if (result.length != input.length) {
            throw new AssertionError("toArray returned array of wrong length");
        }
        for (int i = 0; i < input.length; i++) {
            if (!input[i].equals(result[i])) {
                throw new AssertionError("toArray returned array with wrong elements");
            }
        }
    }

    // Test toArray throws IllegalStateException if pipeline is linked or consumed
    static void testToArrayIllegalStateException() {
        String[] input = {"x", "y"};
        ReferencePipeline<String, String> pipeline = createPipeline(input);

        IntFunction<String[]> generator = new IntFunction<String[]>() {
            public String[] apply(int size) {
                return new String[size];
            }
        };

        // Simulate linkedOrConsumed state by calling toArray twice on the same pipeline
        // The first call should succeed, the second should throw IllegalStateException
        pipeline.toArray(generator); // first call consumes pipeline

        boolean threw = false;
        try {
            pipeline.toArray(generator); // second call should throw
        } catch (IllegalStateException e) {
            threw = true;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalStateException not thrown on second toArray call");
        }
    }
}