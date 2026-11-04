/*
 * @test
 * @summary Test for java.util.stream.ReduceOps.makeInt(IntBinaryOperator) method
 * @run main ReduceOpsWitupTest
 */

import java.util.OptionalInt;
import java.util.function.IntBinaryOperator;
import java.util.stream.ReduceOps;
import java.util.stream.TerminalOp;

public class ReduceOpsWitupTest {
    public static void main(String[] args) {
        testMakeIntWithNullOperator();
        testMakeIntWithSimpleOperator();
    }

    private static void testMakeIntWithNullOperator() {
        try {
            ReduceOps.makeInt(null);
            throw new AssertionError("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testMakeIntWithSimpleOperator() {
        IntBinaryOperator sumOperator = new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left + right;
            }
        };

        TerminalOp<Integer, OptionalInt> op = ReduceOps.makeInt(sumOperator);
        if (op == null) {
            throw new AssertionError("makeInt returned null TerminalOp");
        }

        // Create a sink and test accumulation
        TerminalOp<Integer, OptionalInt> terminalOp = op;
        ReduceOps.ReduceOp<Integer, OptionalInt, ?> reduceOp = (ReduceOps.ReduceOp<Integer, OptionalInt, ?>) terminalOp;
        ReduceOps.AccumulatingSink<Integer, OptionalInt, ?> sink = reduceOp.makeSink();

        // Begin with size 3
        sink.begin(3);
        // Accept values 1, 2, 3
        sink.accept(1);
        sink.accept(2);
        sink.accept(3);

        OptionalInt result = sink.get();
        if (!result.isPresent()) {
            throw new AssertionError("Result should be present after accepting values");
        }
        int value = result.getAsInt();
        if (value != 6) {
            throw new AssertionError("Expected sum 6 but got " + value);
        }

        // Test combine with another sink
        ReduceOps.AccumulatingSink<Integer, OptionalInt, ?> otherSink = reduceOp.makeSink();
        otherSink.begin(2);
        otherSink.accept(4);
        otherSink.accept(5);

        sink.combine(otherSink);
        OptionalInt combinedResult = sink.get();
        if (!combinedResult.isPresent()) {
            throw new AssertionError("Combined result should be present");
        }
        int combinedValue = combinedResult.getAsInt();
        if (combinedValue != 15) { // 6 + 9
            throw new AssertionError("Expected combined sum 15 but got " + combinedValue);
        }
    }
}
