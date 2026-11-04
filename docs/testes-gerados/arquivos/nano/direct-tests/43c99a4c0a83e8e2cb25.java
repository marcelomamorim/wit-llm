/*
 * @test
 * @summary Test for IntPipeline.reduce method with IntBinaryOperator
 * @run main IntPipelineWitupTest
 */
import java.util.OptionalInt;
import java.util.stream.IntPipeline;
import java.util.function.IntBinaryOperator;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testReduceSum();
        testReduceProduct();
        testReduceWithEmpty();
    }

    static void testReduceSum() {
        IntBinaryOperator sumOp = (a, b) -> a + b;
        int[] data = {1, 2, 3, 4};
        OptionalInt result = IntPipeline.of(data).reduce(sumOp);
        if (!result.isPresent()) {
            throw new AssertionError("Expected present result for sum");
        }
        if (result.getAsInt() != 10) {
            throw new AssertionError("Sum reduction failed, expected 10 but got " + result.getAsInt());
        }
    }

    static void testReduceProduct() {
        IntBinaryOperator productOp = (a, b) -> a * b;
        int[] data = {1, 2, 3, 4};
        OptionalInt result = IntPipeline.of(data).reduce(productOp);
        if (!result.isPresent()) {
            throw new AssertionError("Expected present result for product");
        }
        if (result.getAsInt() != 24) {
            throw new AssertionError("Product reduction failed, expected 24 but got " + result.getAsInt());
        }
    }

    static void testReduceWithEmpty() {
        IntBinaryOperator sumOp = (a, b) -> a + b;
        int[] emptyData = {};
        OptionalInt result = IntPipeline.of(emptyData).reduce(sumOp);
        if (result.isPresent()) {
            throw new AssertionError("Expected empty result for empty stream");
        }
    }
}
