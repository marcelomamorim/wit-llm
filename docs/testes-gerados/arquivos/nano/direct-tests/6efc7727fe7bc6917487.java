/*
 * @test
 * @summary Tests for ReferencePipeline.mapToInt method.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.IntStream;
import java.util.function.ToIntFunction;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapToIntWithValidFunction();
        testMapToIntWithNullFunction();
        testMapToIntWithIdentityFunction();
    }

    static void testMapToIntWithValidFunction() {
        // Create a dummy ReferencePipeline with a custom implementation
        ReferencePipeline<String> pipeline = new ReferencePipeline<String>(
            () -> java.util.Spliterators.spliterator(new String[] {"a", "bb"}, 0),
            0, false) {
        };
        ToIntFunction<String> lengthFunction = new ToIntFunction<String>() {
            @Override
            public int applyAsInt(String value) {
                return value.length();
            }
        };
        IntStream intStream = pipeline.mapToInt(lengthFunction);
        int[] result = intStream.toArray();
        assert result.length == 2 : "Expected array length 2";
        assert result[0] == 1 : "First element should be 1";
        assert result[1] == 2 : "Second element should be 2";
    }

    static void testMapToIntWithNullFunction() {
        try {
            ReferencePipeline<String> pipeline = new ReferencePipeline<String>(
                () -> java.util.Spliterators.spliterator(new String[] {"a"}, 0),
                0, false) {
            };
            pipeline.mapToInt(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testMapToIntWithIdentityFunction() {
        // Identity function for String to int is not directly possible, so test with a dummy function
        ReferencePipeline<String> pipeline = new ReferencePipeline<String>(
            () -> java.util.Spliterators.spliterator(new String[] {"abc"}, 0),
            0, false) {
        };
        ToIntFunction<String> identityFunction = new ToIntFunction<String>() {
            @Override
            public int applyAsInt(String value) {
                return value.length();
            }
        };
        IntStream resultStream = pipeline.mapToInt(identityFunction);
        int[] result = resultStream.toArray();
        assert result.length == 1 : "Expected array length 1";
        assert result[0] == 3 : "Element should be 3";
    }
}
