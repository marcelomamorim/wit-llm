/*
 * @test
 * @summary Test for IntPipeline.reduce method to observe behavior with valid input.
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntPipeline;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testReduceWithValidOperator();
        testReduceWithNullOperator();
    }

    private static void testReduceWithValidOperator() {
        IntBinaryOperator sumOperator = (a, b) -> a + b;
        IntPipeline pipeline = IntPipeline.of(new int[]{1, 2, 3, 4});
        OptionalInt result = pipeline.reduce(sumOperator);
        if (result.isPresent()) {
            int value = result.getAsInt();
            if (value != 10) {
                throw new AssertionError("Expected sum 10, but got " + value);
            }
        } else {
            throw new AssertionError("Result should be present for valid input.");
        }
    }

    private static void testReduceWithNullOperator() {
        try {
            IntPipeline pipeline = IntPipeline.of(new int[]{1, 2, 3});
            pipeline.reduce(null);
            throw new AssertionError("Expected NullPointerException for null operator.");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
