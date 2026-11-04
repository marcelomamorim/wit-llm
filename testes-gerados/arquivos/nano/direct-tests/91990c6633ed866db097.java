/*
 * @test
 * @summary Test reduce(BinaryOperator) method of ReferencePipeline
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.stream.ReferencePipeline;
import java.util.function.BinaryOperator;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testReduceWithNonEmptyStream();
        testReduceWithEmptyStream();
    }

    static void testReduceWithNonEmptyStream() {
        BinaryOperator<String> concat = (a, b) -> a + b;
        // Create a stream with elements
        ReferencePipeline<String, String> stream = createStream("a", "b", "c");
        Optional<String> result = stream.reduce(concat);
        if (!result.isPresent()) {
            throw new AssertionError("Expected non-empty result");
        }
        String expected = "abc";
        if (!result.get().equals(expected)) {
            throw new AssertionError("Expected '" + expected + "', but got '" + result.get() + "'");
        }
    }

    static void testReduceWithEmptyStream() {
        BinaryOperator<String> concat = (a, b) -> a + b;
        // Create an empty stream
        ReferencePipeline<String, String> stream = createStream();
        Optional<String> result = stream.reduce(concat);
        if (result.isPresent()) {
            throw new AssertionError("Expected empty result");
        }
    }

    static <T> ReferencePipeline<T, T> createStream(T... elements) {
        return new ReferencePipeline<T, T>() {
            @Override
            public java.util.Spliterator<T> spliterator() {
                return java.util.Spliterators.spliterator(elements, 0);
            }
        };
    }
}
