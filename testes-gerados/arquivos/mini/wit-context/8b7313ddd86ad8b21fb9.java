/*
 * @test
 * @summary Test ReduceOps.makeDouble method for null operator and basic operation
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.ReduceOps;
import java.util.function.DoubleBinaryOperator;
import java.util.Objects;

public class ReduceOpsWitupTest {
    public static void main(String[] args) {
        testMakeDoubleNullOperator();
        testMakeDoubleBasicOperation();
    }

    private static void testMakeDoubleNullOperator() {
        try {
            ReduceOps.makeDouble(0.0, null);
            throw new AssertionError("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testMakeDoubleBasicOperation() {
        DoubleBinaryOperator op = new DoubleBinaryOperator() {
            @Override
            public double applyAsDouble(double left, double right) {
                return left + right;
            }
        };

        ReduceOps.TerminalOp<Double, Double> reduceOp = ReduceOps.makeDouble(0.0, op);
        ReduceOps.AccumulatingSink<Double, Double, ?> sink = reduceOp.makeSink();

        sink.begin(3);
        sink.accept(1.0);
        sink.accept(2.0);
        sink.accept(3.0);

        Double result = sink.get();
        if (!Objects.equals(result, 6.0)) {
            throw new AssertionError("Expected sum 6.0 but got " + result);
        }
    }
}