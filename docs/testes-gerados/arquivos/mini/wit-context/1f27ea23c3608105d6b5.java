/*
 * @test
 * @summary Test ReferencePipeline.anyMatch behavior including IllegalStateException on consumed pipeline
 * @run main ReferencePipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testAnyMatchReturnsTrue();
        testAnyMatchReturnsFalse();
        testAnyMatchThrowsOnConsumedStream();
    }

    private static void testAnyMatchReturnsTrue() {
        Stream<String> stream = Stream.of("a", "b", "c");
        ReferencePipeline<String, String> pipeline = createReferencePipeline(stream);
        boolean result = pipeline.anyMatch(new Predicate<String>() {
            public boolean test(String s) {
                return "b".equals(s);
            }
        });
        if (!result) {
            throw new AssertionError("Expected anyMatch to return true when predicate matches an element");
        }
    }

    private static void testAnyMatchReturnsFalse() {
        Stream<String> stream = Stream.of("a", "b", "c");
        ReferencePipeline<String, String> pipeline = createReferencePipeline(stream);
        boolean result = pipeline.anyMatch(new Predicate<String>() {
            public boolean test(String s) {
                return "z".equals(s);
            }
        });
        if (result) {
            throw new AssertionError("Expected anyMatch to return false when predicate matches no elements");
        }
    }

    private static void testAnyMatchThrowsOnConsumedStream() {
        Stream<String> stream = Stream.of("a", "b", "c");
        ReferencePipeline<String, String> pipeline = createReferencePipeline(stream);
        // First terminal operation consumes the stream
        pipeline.anyMatch(new Predicate<String>() {
            public boolean test(String s) {
                return false;
            }
        });
        try {
            // Second terminal operation on the same pipeline should throw IllegalStateException
            pipeline.anyMatch(new Predicate<String>() {
                public boolean test(String s) {
                    return true;
                }
            });
            throw new AssertionError("Expected IllegalStateException when calling anyMatch on consumed pipeline");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    // Helper method to create ReferencePipeline from Stream
    private static <T> ReferencePipeline<T, T> createReferencePipeline(Stream<T> stream) {
        // Stream implements BaseStream which extends AutoCloseable and has spliterator()
        // ReferencePipeline has a constructor ReferencePipeline(Spliterator<?>, int, boolean)
        // We use spliterator() and source flags 0 (no flags) and parallel false
        Spliterator<T> spliterator = stream.spliterator();
        return new ReferencePipeline<T, T>(spliterator, 0, stream.isParallel()) {
            // Anonymous subclass to instantiate abstract ReferencePipeline
            // Delegate evaluate method to stream's terminal operation
            @Override
            public <R, P_IN> R evaluate(java.util.stream.PipelineHelper<R> helper, Spliterator<P_IN> spliterator, boolean flatten) {
                throw new UnsupportedOperationException("Not used in this test");
            }

            @Override
            public boolean evaluate(java.util.stream.TerminalOp<T, Boolean> terminalOp) {
                // Delegate to stream terminal operation
                return stream.collect(java.util.stream.Collectors.toList()).stream().anyMatch(terminalOp.getOp());
            }
        };
    }
}