/*
 * @test
 * @summary Test ReferencePipeline.reduce(BinaryOperator) for normal and consumed state
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testReduceWithValidAccumulator();
        testReduceOnConsumedStream();
    }

    private static void testReduceWithValidAccumulator() {
        Stream<String> stream = Stream.of("a", "b", "c");
        Optional<String> result = stream.reduce(new BinaryOperator<String>() {
            @Override
            public String apply(String s1, String s2) {
                return s1 + s2;
            }
        });
        if (!result.isPresent() || !"abc".equals(result.get())) {
            throw new AssertionError("Expected concatenation of 'abc', got: " + result);
        }
    }

    private static void testReduceOnConsumedStream() {
        Stream<String> stream = Stream.of("x", "y");
        // Consume the stream first
        stream.count();
        try {
            stream.reduce(new BinaryOperator<String>() {
                @Override
                public String apply(String s1, String s2) {
                    return s1 + s2;
                }
            });
            throw new AssertionError("Expected IllegalStateException when reducing a consumed stream");
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Expected IllegalStateException but got " + e);
        }
    }
}
