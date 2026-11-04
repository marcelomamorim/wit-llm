/*
 * @test
 * @summary Test IntPipeline.reduce(IntBinaryOperator) with simple and edge cases
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.function.IntBinaryOperator;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testReduceSum();
        testReduceMax();
        testReduceEmpty();
    }

    private static void testReduceSum() {
        IntStream stream = IntStream.of(1, 2, 3, 4);
        IntBinaryOperator sumOp = (a, b) -> a + b;
        OptionalInt result = stream.reduce(sumOp);
        if (!result.isPresent() || result.getAsInt() != 10) {
            throw new AssertionError("Expected sum 10 but got " + result);
        }
    }

    private static void testReduceMax() {
        IntStream stream = IntStream.of(5, 3, 9, 1);
        IntBinaryOperator maxOp = (a, b) -> a > b ? a : b;
        OptionalInt result = stream.reduce(maxOp);
        if (!result.isPresent() || result.getAsInt() != 9) {
            throw new AssertionError("Expected max 9 but got " + result);
        }
    }

    private static void testReduceEmpty() {
        IntStream emptyStream = IntStream.empty();
        IntBinaryOperator sumOp = (a, b) -> a + b;
        OptionalInt result = emptyStream.reduce(sumOp);
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalInt but got " + result);
        }
    }
}
