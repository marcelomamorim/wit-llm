/*
 * @test
 * @summary Test for DoublePipeline.reduce(double, DoubleBinaryOperator) method
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.function.DoubleBinaryOperator;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testReduceWithValidOperator();
        testReduceWithNullOperator();
    }

    private static void testReduceWithValidOperator() {
        double[] data = {1.0, 2.0, 3.0};
        DoubleBinaryOperator sumOperator = (a, b) -> a + b;
        double result = DoublePipeline.of(data).reduce(0.0, sumOperator);
        if (result != 6.0) {
            throw new AssertionError("Expected 6.0, but got " + result);
        }
    }

    private static void testReduceWithNullOperator() {
        double[] data = {1.0, 2.0, 3.0};
        try {
            DoublePipeline.of(data).reduce(0.0, null);
            throw new AssertionError("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // expected exception
        }
    }
}
