/*
 * @test
 * @summary Test ReferencePipeline.findFirst() for normal and consumed state
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.ReferencePipeline;
import java.util.stream.StreamSupport;
import java.util.stream.Stream;
import java.util.Spliterators;
import java.util.function.Supplier;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFindFirstNormal();
        testFindFirstConsumed();
    }

    // Helper method to create a ReferencePipeline from an array
    static <T> ReferencePipeline<T, T> createPipeline(final T[] elements) {
        Supplier<Spliterator<T>> supplier = new Supplier<Spliterator<T>>() {
            public Spliterator<T> get() {
                return Spliterators.spliterator(elements, 0);
            }
        };
        // Use the public constructor ReferencePipeline(Supplier<? extends Spliterator<?>>, int, boolean)
        // sourceFlags and parallel set to 0 and false respectively
        return new ReferencePipeline<T, T>(supplier, 0, false) {
            // Implement abstract methods by delegating to AbstractPipeline
            // No additional override needed for this test
        };
    }

    // Test normal usage of findFirst on a non-empty stream
    static void testFindFirstNormal() {
        String[] data = {"a", "b", "c"};
        ReferencePipeline<String, String> pipeline = createPipeline(data);
        Optional<String> result = pipeline.findFirst();
        if (!result.isPresent() || !"a".equals(result.get())) {
            throw new AssertionError("Expected Optional containing 'a', got: " + result);
        }
    }

    // Test that calling findFirst twice on the same pipeline throws IllegalStateException
    static void testFindFirstConsumed() {
        String[] data = {"x", "y"};
        ReferencePipeline<String, String> pipeline = createPipeline(data);
        Optional<String> firstCall = pipeline.findFirst();
        if (!firstCall.isPresent() || !"x".equals(firstCall.get())) {
            throw new AssertionError("Expected Optional containing 'x' on first call, got: " + firstCall);
        }
        try {
            pipeline.findFirst();
            throw new AssertionError("Expected IllegalStateException on second findFirst call");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

}
