/*
 * @test
 * @summary Test java.util.stream.ReduceOps.makeDouble method behavior
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.ReduceOps;
import java.util.stream.StreamShape;
import java.util.function.DoubleBinaryOperator;

public class ReduceOpsWitupTest {
    public static void main(String[] args) {
        testMakeDoubleWithSumOperator();
        testMakeDoubleWithIdentityOnly();
        testMakeDoubleWithOperatorThatSubtracts();
    }

    private static void testMakeDoubleWithSumOperator() {
        final double identity = 0.0d;
        DoubleBinaryOperator sumOperator = new DoubleBinaryOperator() {
            @Override
            public double applyAsDouble(double left, double right) {
                return left + right;
            }
        };

        ReduceOps.ReduceOp<Double, Double, ?> op = ReduceOps.makeDouble(identity, sumOperator);
        ReduceOps.AccumulatingSink<Double, Double, ?> sink = op.makeSink();

        sink.begin(3);
        sink.accept(1.0d);
        sink.accept(2.0d);
        sink.accept(3.0d);
        Double result = sink.get();

        if (result.doubleValue() != 6.0d) {
            throw new AssertionError("Sum operator test failed: expected 6.0 but got " + result);
        }
    }

    private static void testMakeDoubleWithIdentityOnly() {
        final double identity = 42.0d;
        DoubleBinaryOperator operator = new DoubleBinaryOperator() {
            @Override
            public double applyAsDouble(double left, double right) {
                return left; // ignore right, always return left
            }
        };

        ReduceOps.ReduceOp<Double, Double, ?> op = ReduceOps.makeDouble(identity, operator);
        ReduceOps.AccumulatingSink<Double, Double, ?> sink = op.makeSink();

        sink.begin(0);
        Double result = sink.get();

        if (result.doubleValue() != identity) {
            throw new AssertionError("Identity only test failed: expected " + identity + " but got " + result);
        }
    }

    private static void testMakeDoubleWithOperatorThatSubtracts() {
        final double identity = 10.0d;
        DoubleBinaryOperator subtractOperator = new DoubleBinaryOperator() {
            @Override
            public double applyAsDouble(double left, double right) {
                return left - right;
            }
        };

        ReduceOps.ReduceOp<Double, Double, ?> op = ReduceOps.makeDouble(identity, subtractOperator);
        ReduceOps.AccumulatingSink<Double, Double, ?> sink1 = op.makeSink();
        ReduceOps.AccumulatingSink<Double, Double, ?> sink2 = op.makeSink();

        sink1.begin(2);
        sink1.accept(3.0d);
        sink1.accept(1.0d);

        sink2.begin(1);
        sink2.accept(2.0d);

        // Combine sink2 into sink1
        ((ReduceOps.AccumulatingSink<Double, Double, ?>)sink1).combine((ReduceOps.AccumulatingSink<Double, Double, ?>)sink2);

        Double result = sink1.get();

        // Calculation: sink1 state after accepts: ((10 - 3) - 1) = 6
        // Combining sink2: sink1.accept(sink2.state) => 6 - (10 - 2) = 6 - 8 = -2
        if (result.doubleValue() != -2.0d) {
            throw new AssertionError("Subtract operator combine test failed: expected -2.0 but got " + result);
        }
    }
}
