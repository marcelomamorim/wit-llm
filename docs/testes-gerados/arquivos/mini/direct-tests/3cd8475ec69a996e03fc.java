/*
 * @test
 * @summary Test ReferencePipeline.toArray(IntFunction) with simple and edge cases
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.IntFunction;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testToArrayWithSimpleStream();
        testToArrayWithEmptyStream();
        testToArrayWithNullGenerator();
    }

    static void testToArrayWithSimpleStream() {
        Stream<String> stream = Stream.of("a", "b", "c");
        ReferencePipeline<String, String> pipeline = (ReferencePipeline<String, String>) stream;

        String[] result = pipeline.toArray(new IntFunction<String[]>() {
            public String[] apply(int size) {
                return new String[size];
            }
        });

        if (result.length != 3) {
            throw new AssertionError("Expected array length 3 but got " + result.length);
        }
        if (!"a".equals(result[0]) || !"b".equals(result[1]) || !"c".equals(result[2])) {
            throw new AssertionError("Array contents do not match stream elements");
        }
    }

    static void testToArrayWithEmptyStream() {
        Stream<String> stream = Stream.empty();
        ReferencePipeline<String, String> pipeline = (ReferencePipeline<String, String>) stream;

        String[] result = pipeline.toArray(new IntFunction<String[]>() {
            public String[] apply(int size) {
                return new String[size];
            }
        });

        if (result.length != 0) {
            throw new AssertionError("Expected empty array but got length " + result.length);
        }
    }

    static void testToArrayWithNullGenerator() {
        Stream<String> stream = Stream.of("x");
        ReferencePipeline<String, String> pipeline = (ReferencePipeline<String, String>) stream;

        try {
            pipeline.toArray(null);
            throw new AssertionError("Expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
