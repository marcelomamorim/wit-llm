/*
 * @test
 * @summary Tests the reduce method of DoublePipeline for correct reduction behavior.
 * @run main DoublePipelineWitupTest
 */
import java.util.stream.DoublePipeline;
import java.util.function.DoubleBinaryOperator;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testReduceSum();
        testReduceProduct();
        testReduceWithEmptyStream();
    }

    static void testReduceSum() {
        DoubleBinaryOperator sumOp = (a, b) -> a + b;
        double[] data = {1.0, 2.0, 3.0, 4.0};
        double result = DoublePipeline.of(data).reduce(0.0, sumOp);
        assert result == 10.0 : "Expected sum 10.0, but got " + result;
    }

    static void testReduceProduct() {
        DoubleBinaryOperator productOp = (a, b) -> a * b;
        double[] data = {1.0, 2.0, 3.0, 4.0};
        double result = DoublePipeline.of(data).reduce(1.0, productOp);
        assert result == 24.0 : "Expected product 24.0, but got " + result;
    }

    static void testReduceWithEmptyStream() {
        DoubleBinaryOperator sumOp = (a, b) -> a + b;
        double[] emptyData = {};
        double result = DoublePipeline.of(emptyData).reduce(0.0, sumOp);
        assert result == 0.0 : "Expected sum 0.0 for empty stream, but got " + result;
    }
}
