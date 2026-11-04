/*
 * @test
 * @summary Test ReferencePipeline.mapToLong behavior and null argument handling
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.LongStream;
import java.util.function.ToLongFunction;
import java.util.Spliterator;
import java.util.stream.StreamSupport;
import java.util.Objects;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapToLongWithValidMapper();
        testMapToLongWithNullMapper();
    }

    static void testMapToLongWithValidMapper() {
        // Create a ReferencePipeline from a simple source
        Spliterator<String> spliterator = java.util.Arrays.asList("a", "bb", "ccc").spliterator();
        ReferencePipeline<String, String> pipeline = new ReferencePipeline<String, String>(spliterator, 0, false) {
            @Override
            public java.util.stream.Stream<String> opWrapSink(int flags, java.util.stream.Sink<String> sink) {
                throw new UnsupportedOperationException();
            }
        };

        // Use mapToLong with a mapper that returns string length
        ToLongFunction<String> mapper = new ToLongFunction<String>() {
            public long applyAsLong(String s) {
                return s.length();
            }
        };

        LongStream longStream = pipeline.mapToLong(mapper);

        // Collect results to array and verify
        long[] results = longStream.toArray();

        // Expected lengths
        long[] expected = {1L, 2L, 3L};

        if (results.length != expected.length) {
            throw new AssertionError("Result length mismatch");
        }
        for (int i = 0; i < results.length; i++) {
            if (results[i] != expected[i]) {
                throw new AssertionError("Result element mismatch at index " + i);
            }
        }
    }

    static void testMapToLongWithNullMapper() {
        // Create a ReferencePipeline from a simple source
        Spliterator<String> spliterator = java.util.Arrays.asList("x").spliterator();
        ReferencePipeline<String, String> pipeline = new ReferencePipeline<String, String>(spliterator, 0, false) {
            @Override
            public java.util.stream.Stream<String> opWrapSink(int flags, java.util.stream.Sink<String> sink) {
                throw new UnsupportedOperationException();
            }
        };

        // Pass null mapper and verify NullPointerException is thrown
        try {
            pipeline.mapToLong(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

}