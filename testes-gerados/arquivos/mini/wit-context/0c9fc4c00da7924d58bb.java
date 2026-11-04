/*
 * @test
 * @summary Deterministic tests for java.util.stream.ReduceOps.makeInt method
 * @run main ReduceOpsWitupTest
 */

import java.util.Objects;
import java.util.function.IntBinaryOperator;
import java.util.stream.ReduceOps;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        testMakeIntNullOperator();
        testMakeIntSimpleSum();
    }

    private static void testMakeIntNullOperator() {
        try {
            ReduceOps.makeInt(0, null);
            throw new AssertionError("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testMakeIntSimpleSum() {
        IntBinaryOperator sumOperator = new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left + right;
            }
        };

        ReduceOps.TerminalOp<Integer, Integer> op = ReduceOps.makeInt(0, sumOperator);
        ReduceOps.AccumulatingSink<Integer, Integer, ?> sink = op.makeSink();

        // Begin with size 3 (arbitrary)
        sink.begin(3);

        // Accept values 1, 2, 3
        sink.accept(1);
        sink.accept(2);
        sink.accept(3);

        // The state should be 0 + 1 + 2 + 3 = 6
        Integer result = sink.get();
        if (result == null || result.intValue() != 6) {
            throw new AssertionError("Expected sum 6 but got " + result);
        }

        // Test combine: combine with another sink with state 4
        ReduceOps.AccumulatingSink<Integer, Integer, ?> otherSink = op.makeSink();
        otherSink.begin(1);
        otherSink.accept(4);

        // Combine sinks
        sink.combine(otherSink);

        // Now result should be 6 + 4 = 10
        Integer combinedResult = sink.get();
        if (combinedResult == null || combinedResult.intValue() != 10) {
            throw new AssertionError("Expected combined sum 10 but got " + combinedResult);
        }
    }
}