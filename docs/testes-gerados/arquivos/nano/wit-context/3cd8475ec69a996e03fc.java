/*
 * @test
 * @summary Tests for ReferencePipeline.toArray method to verify behavior and exception handling.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.function.IntFunction;
import java.util.Arrays;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testToArrayWithValidGenerator();
        testToArrayWithNullGenerator();
        testToArrayWithGeneratorThatThrows();
    }

    static void testToArrayWithValidGenerator() {
        try {
            // Create a simple stream with known elements
            Integer[] array = {1, 2, 3};
            Integer[] result = Arrays.stream(array)
                .map(i -> i * 2)
                .collect(ReferencePipeline.toArray(Integer[]::new));
            // Verify the result contains doubled values
            if (result.length != 3 || result[0] != 2 || result[1] != 4 || result[2] != 6) {
                throw new AssertionError("Unexpected array contents: " + Arrays.toString(result));
            }
        } catch (Throwable t) {
            throw new AssertionError("Failed in testToArrayWithValidGenerator", t);
        }
    }

    static void testToArrayWithNullGenerator() {
        try {
            // Passing null should throw NullPointerException
            Integer[] result = Arrays.stream(new Integer[]{1, 2, 3})
                .collect(ReferencePipeline.toArray(null));
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type", t);
        }
    }

    static void testToArrayWithGeneratorThatThrows() {
        try {
            IntFunction<Integer[]> faultyGenerator = size -> {
                throw new RuntimeException("Generator failure");
            };
            // This should propagate the RuntimeException
            Arrays.stream(new Integer[]{1, 2, 3})
                .collect(ReferencePipeline.toArray(faultyGenerator));
            throw new AssertionError("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            if (!"Generator failure".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message", e);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type", t);
        }
    }
}
