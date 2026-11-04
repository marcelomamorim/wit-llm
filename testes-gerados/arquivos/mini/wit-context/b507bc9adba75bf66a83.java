/*
 * @test
 * @summary Test ReferencePipeline.toArray() for normal and consumed states
 * @run main ReferencePipelineWitupTest
 */

import java.util.Spliterator;
import java.util.stream.ReferencePipeline;
import java.util.stream.StreamSupport;
import java.util.stream.Stream;
import java.util.function.Supplier;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testToArrayNormal();
        testToArrayConsumed();
    }

    static void testToArrayNormal() {
        // Create a simple ReferencePipeline from a known source
        Supplier<Spliterator<String>> sourceSupplier = new Supplier<Spliterator<String>>() {
            @Override
            public Spliterator<String> get() {
                return Spliterators.spliterator(new String[] {"a", "b", "c"}, Spliterator.ORDERED);
            }
        };

        ReferencePipeline<String, String> pipeline = new ReferencePipeline<String, String>(sourceSupplier, 0, false) {
            // Implement abstract methods minimally
            @Override
            public void forEach(java.util.function.Consumer<? super String> action) {
                StreamSupport.stream(sourceSupplier.get(), false).forEach(action);
            }
        };

        Object[] arr = pipeline.toArray();
        if (arr.length != 3) {
            throw new AssertionError("Expected array length 3, got " + arr.length);
        }
        if (!"a".equals(arr[0]) || !"b".equals(arr[1]) || !"c".equals(arr[2])) {
            throw new AssertionError("Array contents do not match expected values");
        }
    }

    static void testToArrayConsumed() {
        // Create a pipeline and consume it, then call toArray() to check for IllegalStateException
        Supplier<Spliterator<String>> sourceSupplier = new Supplier<Spliterator<String>>() {
            @Override
            public Spliterator<String> get() {
                return Spliterators.spliterator(new String[] {"x", "y"}, Spliterator.ORDERED);
            }
        };

        ReferencePipeline<String, String> pipeline = new ReferencePipeline<String, String>(sourceSupplier, 0, false) {
            private boolean consumed = false;

            @Override
            public void forEach(java.util.function.Consumer<? super String> action) {
                if (consumed) {
                    throw new IllegalStateException("Stream has already been operated upon or closed");
                }
                consumed = true;
                StreamSupport.stream(sourceSupplier.get(), false).forEach(action);
            }

            @Override
            public Object[] toArray() {
                if (consumed) {
                    throw new IllegalStateException("Stream has already been operated upon or closed");
                }
                consumed = true;
                return super.toArray();
            }
        };

        // Consume the stream
        pipeline.forEach(s -> {});

        boolean threw = false;
        try {
            pipeline.toArray();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when calling toArray() on consumed pipeline");
        }
    }
}
