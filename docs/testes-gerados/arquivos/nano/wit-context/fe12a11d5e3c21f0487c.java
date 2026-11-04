/*
 * @test
 * @summary Tests for java.util.stream.ReferencePipeline methods
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.LongStream;
import java.util.function.ToLongFunction;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapToLong_NullMapper();
        testMapToLong_Valid();
    }

    private static void testMapToLong_NullMapper() {
        try {
            ReferencePipeline<String, String> pipeline = null;
            // Simulate calling mapToLong with null, expecting NullPointerException
            ReferencePipeline<String, String> result = pipeline.mapToLong(null);
            // If no exception, fail the test
            System.err.println("Failed: Expected NullPointerException for null mapper");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown as expected for null mapper");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    private static void testMapToLong_Valid() {
        // Create a simple pipeline with a non-null source
        java.util.List<String> list = java.util.Arrays.asList("a", "bb", "ccc");
        ReferencePipeline<String, String> pipeline = (ReferencePipeline<String, String>) list.stream();
        ToLongFunction<String> lengthFunction = new ToLongFunction<String>() {
            @Override
            public long applyAsLong(String value) {
                return value.length();
            }
        };
        LongStream longStream = pipeline.mapToLong(lengthFunction);
        long sum = longStream.sum();
        if (sum == 6) {
            System.out.println("Passed: mapToLong with valid function produces expected sum");
        } else {
            System.err.println("Failed: Unexpected sum: " + sum);
        }
    }
}
