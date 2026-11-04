/*
 * @test
 * @summary Test the reduce method of DoublePipeline with a simple addition operator.
 * @run main DoublePipelineWitupTest
 */
import java.util.OptionalDouble;
import java.util.stream.DoublePipeline;
import java.util.function.DoubleBinaryOperator;

public class DoublePipelineWitupTest {
    public static void main(String[] args) {
        testReduceSum();
        testReduceEmpty();
    }

    static void testReduceSum() {
        DoubleBinaryOperator sumOp = (a, b) -> a + b;
        DoublePipeline pipeline = createPipelineWithValues(1.0, 2.0, 3.0);
        OptionalDouble result = pipeline.reduce(sumOp);
        if (!result.isPresent()) {
            throw new AssertionError("Expected non-empty OptionalDouble");
        }
        if (result.getAsDouble() != 6.0) {
            throw new AssertionError("Expected sum 6.0, but got " + result.getAsDouble());
        }
    }

    static void testReduceEmpty() {
        DoublePipeline emptyPipeline = createEmptyPipeline();
        DoubleBinaryOperator sumOp = (a, b) -> a + b;
        OptionalDouble result = emptyPipeline.reduce(sumOp);
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalDouble for empty pipeline");
        }
    }

    static DoublePipeline createPipelineWithValues(double... values) {
        return DoublePipeline.fromArray(values);
    }

    static DoublePipeline createEmptyPipeline() {
        return DoublePipeline.empty();
    }
}
