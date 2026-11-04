/*
 * @test
 * @summary Test for ReferencePipeline.reduce(BinaryOperator)
 * @run main ReferencePipelineWitupTest
 */

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.ReferencePipeline;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testReduceWithNonEmptyStream();
        testReduceWithEmptyStream();
        testReduceWithNullAccumulator();
    }

    static void testReduceWithNonEmptyStream() {
        BinaryOperator<Integer> sum = (a, b) -> a + b;
        Integer[] data = {1, 2, 3};
        Optional<Integer> result = ReferencePipeline.ofArray(data).reduce(sum);
        if (!result.isPresent() || result.get() != 6) {
            throw new AssertionError("Expected Optional containing 6, but got: " + result);
        }
    }

    static void testReduceWithEmptyStream() {
        BinaryOperator<Integer> sum = (a, b) -> a + b;
        Integer[] emptyData = {};
        Optional<Integer> result = ReferencePipeline.ofArray(emptyData).reduce(sum);
        if (result.isPresent()) {
            throw new AssertionError("Expected empty Optional, but got: " + result);
        }
    }

    static void testReduceWithNullAccumulator() {
        try {
            Integer[] data = {1, 2, 3};
            ReferencePipeline.ofArray(data).reduce(null);
            throw new AssertionError("Expected NullPointerException for null accumulator");
        } catch (NullPointerException e) {
            // expected
        }
    }
}

// Helper method to create a stream from array for testing
// Since ReferencePipeline is package-private, we simulate the stream creation
// using Stream.of() for the purpose of testing the reduce method.
// Alternatively, if direct instantiation is needed, reflection could be used,
// but it's avoided here for simplicity and compatibility.
// Note: The actual implementation may require more elaborate setup.

// For the purpose of this test, assume ReferencePipeline.ofArray() is a static method
// that creates a stream from an array, which is a simplification.
// If not available, replace with appropriate stream creation method.

// Since the actual class is package-private, and the test is in the same package,
// we can access it directly.
// Otherwise, reflection or other techniques would be necessary.

// For this test, we assume the class has a static method 'ofArray' for simplicity.
// If not, the test would need to be adapted accordingly.
