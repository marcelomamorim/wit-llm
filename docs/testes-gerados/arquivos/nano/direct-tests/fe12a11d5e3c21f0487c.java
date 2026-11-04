/*
 * @test
 * @summary Test for ReferencePipeline.mapToLong method to ensure correct long stream mapping.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.LongStream;
import java.util.function.ToLongFunction;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapToLongWithValidFunction();
        testMapToLongWithNullFunction();
    }

    static void testMapToLongWithValidFunction() {
        // Create a simple source stream with Integer objects
        java.util.stream.Stream<Integer> sourceStream = java.util.Arrays.asList(1, 2, 3).stream();
        // Map to LongStream using a lambda that converts Integer to long
        LongStream resultStream = sourceStream
            .mapToLong(new ToLongFunction<Integer>() {
                @Override
                public long applyAsLong(Integer value) {
                    return value * 10L;
                }
            });
        // Collect results into array
        long[] resultArray = resultStream.toArray();
        // Verify the result
        if (resultArray.length != 3) {
            throw new AssertionError("Expected array length 3, got " + resultArray.length);
        }
        if (resultArray[0] != 10L || resultArray[1] != 20L || resultArray[2] != 30L) {
            throw new AssertionError("Unexpected mapping results: " + java.util.Arrays.toString(resultArray));
        }
        System.out.println("testMapToLongWithValidFunction passed.");
    }

    static void testMapToLongWithNullFunction() {
        try {
            // Create a source stream
            java.util.stream.Stream<Integer> sourceStream = java.util.Arrays.asList(1, 2, 3).stream();
            // Call mapToLong with null, expecting NullPointerException
            sourceStream.mapToLong(null);
            throw new AssertionError("Expected NullPointerException not thrown.");
        } catch (NullPointerException e) {
            System.out.println("testMapToLongWithNullFunction passed.");
        }
    }
}
