/*
 * @test
 * @summary Test java.util.stream.ReduceOps.makeInt method behavior
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.ReduceOps;
import java.util.stream.StreamShape;
import java.util.function.IntBinaryOperator;

public class ReduceOpsWitupTest {
    public static void main(String[] args) {
        testMakeIntWithSumOperator();
        testMakeIntWithMultiplyOperator();
        testMakeIntWithIdentityOperator();
    }

    private static void testMakeIntWithSumOperator() {
        IntBinaryOperator sumOperator = new IntBinaryOperator() {
            public int applyAsInt(int left, int right) {
                return left + right;
            }
        };
        ReduceOps.ReduceOp<Integer, Integer, ?> op = ReduceOps.makeInt(0, sumOperator);
        ReduceOps.AccumulatingSink<Integer, Integer, ?> sink = op.makeSink();
        sink.begin(3);
        sink.accept(1);
        sink.accept(2);
        sink.accept(3);
        Integer result = sink.get();
        if (result != 6) {
            throw new AssertionError("Expected sum 6 but got " + result);
        }
    }

    private static void testMakeIntWithMultiplyOperator() {
        IntBinaryOperator multiplyOperator = new IntBinaryOperator() {
            public int applyAsInt(int left, int right) {
                return left * right;
            }
        };
        ReduceOps.ReduceOp<Integer, Integer, ?> op = ReduceOps.makeInt(1, multiplyOperator);
        ReduceOps.AccumulatingSink<Integer, Integer, ?> sink = op.makeSink();
        sink.begin(4);
        sink.accept(2);
        sink.accept(3);
        sink.accept(4);
        sink.accept(5);
        Integer result = sink.get();
        if (result != 120) {
            throw new AssertionError("Expected product 120 but got " + result);
        }
    }

    private static void testMakeIntWithIdentityOperator() {
        IntBinaryOperator identityOperator = new IntBinaryOperator() {
            public int applyAsInt(int left, int right) {
                return left;
            }
        };
        ReduceOps.ReduceOp<Integer, Integer, ?> op = ReduceOps.makeInt(42, identityOperator);
        ReduceOps.AccumulatingSink<Integer, Integer, ?> sink = op.makeSink();
        sink.begin(2);
        sink.accept(100);
        sink.accept(200);
        Integer result = sink.get();
        if (result != 42) {
            throw new AssertionError("Expected identity 42 but got " + result);
        }
    }
}
