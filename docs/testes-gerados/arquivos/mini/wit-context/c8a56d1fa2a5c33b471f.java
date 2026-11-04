/*
 * @test
 * @summary Test ReferencePipeline.reduce method for correct behavior and IllegalStateException on consumed pipeline
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        testReduceWithSimpleStream();
        testReduceOnConsumedStream();
    }

    private static void testReduceWithSimpleStream() {
        Stream<String> stream = Stream.of("a", "b", "c");
        ReferencePipeline<String, String> refPipeline = (ReferencePipeline<String, String>) stream;

        String result = refPipeline.reduce("", new BiFunction<String, String, String>() {
            @Override
            public String apply(String s1, String s2) {
                return s1 + s2;
            }
        }, new BinaryOperator<String>() {
            @Override
            public String apply(String s1, String s2) {
                return s1 + s2;
            }
        });

        if (!"abc".equals(result)) {
            throw new AssertionError("Expected concatenation 'abc' but got: " + result);
        }
    }

    private static void testReduceOnConsumedStream() {
        Stream<String> stream = Stream.of("x", "y");
        ReferencePipeline<String, String> refPipeline = (ReferencePipeline<String, String>) stream;

        // Consume the stream once
        refPipeline.reduce("", new BiFunction<String, String, String>() {
            @Override
            public String apply(String s1, String s2) {
                return s1 + s2;
            }
        }, new BinaryOperator<String>() {
            @Override
            public String apply(String s1, String s2) {
                return s1 + s2;
            }
        });

        // Second reduce call should throw IllegalStateException due to consumed pipeline
        try {
            refPipeline.reduce("", new BiFunction<String, String, String>() {
                @Override
                public String apply(String s1, String s2) {
                    return s1 + s2;
                }
            }, new BinaryOperator<String>() {
                @Override
                public String apply(String s1, String s2) {
                    return s1 + s2;
                }
            });
            throw new AssertionError("Expected IllegalStateException on second reduce call");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
