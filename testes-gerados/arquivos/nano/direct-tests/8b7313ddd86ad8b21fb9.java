/* @test    @summary Test for ReduceOps.makeDouble method behavior    @run main ReduceOpsWitupTest */

import java.util.stream.ReduceOps;
import java.util.function.DoubleBinaryOperator;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        testMakeDoubleWithSum();
        testMakeDoubleWithMax();
        testMakeDoubleWithMin();
    }

    private static void testMakeDoubleWithSum() {
        DoubleBinaryOperator sumOperator = (a, b) -> a + b;
        ReduceOps.ReduceDouble reduceDouble = ReduceOps.makeDouble(0.0, sumOperator);
        ReduceOps.ReduceOps.ReduceOp<Double, Double, ?> op = reduceDouble;
        ReduceOps.ReduceOps.ReducingSink sink = (ReduceOps.ReduceOps.ReducingSink) op.makeSink();
        sink.begin(3);
        sink.accept(1.0);
        sink.accept(2.0);
        sink.accept(3.0);
        Double result = sink.get();
        assert result.equals(6.0) : "Sum reduction failed, got " + result;
    }

    private static void testMakeDoubleWithMax() {
        DoubleBinaryOperator maxOperator = (a, b) -> a > b ? a : b;
        ReduceOps.ReduceDouble reduceDouble = ReduceOps.makeDouble(Double.NEGATIVE_INFINITY, maxOperator);
        ReduceOps.ReduceOps.ReduceOp<Double, Double, ?> op = reduceDouble;
        ReduceOps.ReduceOps.ReducingSink sink = (ReduceOps.ReduceOps.ReducingSink) op.makeSink();
        sink.begin(4);
        sink.accept(1.0);
        sink.accept(4.0);
        sink.accept(2.0);
        sink.accept(3.0);
        Double result = sink.get();
        assert result.equals(4.0) : "Max reduction failed, got " + result;
    }

    private static void testMakeDoubleWithMin() {
        DoubleBinaryOperator minOperator = (a, b) -> a < b ? a : b;
        ReduceOps.ReduceDouble reduceDouble = ReduceOps.makeDouble(Double.POSITIVE_INFINITY, minOperator);
        ReduceOps.ReduceOps.ReduceOp<Double, Double, ?> op = reduceDouble;
        ReduceOps.ReduceOps.ReducingSink sink = (ReduceOps.ReduceOps.ReducingSink) op.makeSink();
        sink.begin(4);
        sink.accept(1.0);
        sink.accept(4.0);
        sink.accept(2.0);
        sink.accept(3.0);
        Double result = sink.get();
        assert result.equals(1.0) : "Min reduction failed, got " + result;
    }
}
