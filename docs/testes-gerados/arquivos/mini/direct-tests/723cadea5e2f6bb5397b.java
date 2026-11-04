/*
 * @test
 * @summary Test DoublePipeline.reduce(DoubleBinaryOperator) behavior
 * @run main DoublePipelineWitupTest
 */

import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {
    public static void main(String[] args) {
        testReduceSum();
        testReduceMax();
        testReduceEmptyStream();
    }

    private static void testReduceSum() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        DoubleBinaryOperator sumOp = (a, b) -> a + b;
        OptionalDouble result = stream.reduce(sumOp);
        if (!result.isPresent() || result.getAsDouble() != 6.0) {
            throw new AssertionError("Expected sum 6.0 but got " + result);
        }
    }

    private static void testReduceMax() {
        DoubleStream stream = DoubleStream.of(1.0, 5.0, 3.0);
        DoubleBinaryOperator maxOp = (a, b) -> a > b ? a : b;
        OptionalDouble result = stream.reduce(maxOp);
        if (!result.isPresent() || result.getAsDouble() != 5.0) {
            throw new AssertionError("Expected max 5.0 but got " + result);
        }
    }

    private static void testReduceEmptyStream() {
        DoubleStream stream = DoubleStream.empty();
        DoubleBinaryOperator sumOp = (a, b) -> a + b;
        OptionalDouble result = stream.reduce(sumOp);
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalDouble for empty stream but got " + result);
        }
    }
}
