/*
 * @test
 * @summary Test java.util.stream.ReduceOps.makeInt(IntBinaryOperator) behavior
 * @run main ReduceOpsWitupTest
 */

import java.util.OptionalInt;
import java.util.function.IntBinaryOperator;
import java.util.stream.ReduceOps;
import java.util.stream.StreamShape;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        testMakeIntWithSumOperator();
        testMakeIntWithMinOperator();
        testMakeIntWithSingleElement();
        testMakeIntWithNoElements();
        testMakeIntWithNullOperator();
    }

    private static void testMakeIntWithSumOperator() {
        IntBinaryOperator sum = (a, b) -> a + b;
        ReduceOps.TerminalOp<Integer, OptionalInt> op = ReduceOps.makeInt(sum);
        ReduceOps.AccumulatingSink<Integer, OptionalInt, ?> sink = op.makeSink();

        sink.begin(3);
        sink.accept(1);
        sink.accept(2);
        sink.accept(3);

        OptionalInt result = sink.get();
        if (!result.isPresent() || result.getAsInt() != 6) {
            throw new AssertionError("Sum operator failed: expected 6 but got " + result);
        }
    }

    private static void testMakeIntWithMinOperator() {
        IntBinaryOperator min = (a, b) -> a < b ? a : b;
        ReduceOps.TerminalOp<Integer, OptionalInt> op = ReduceOps.makeInt(min);
        ReduceOps.AccumulatingSink<Integer, OptionalInt, ?> sink = op.makeSink();

        sink.begin(4);
        sink.accept(10);
        sink.accept(5);
        sink.accept(7);
        sink.accept(3);

        OptionalInt result = sink.get();
        if (!result.isPresent() || result.getAsInt() != 3) {
            throw new AssertionError("Min operator failed: expected 3 but got " + result);
        }
    }

    private static void testMakeIntWithSingleElement() {
        IntBinaryOperator opFunc = (a, b) -> a * b; // arbitrary
        ReduceOps.TerminalOp<Integer, OptionalInt> op = ReduceOps.makeInt(opFunc);
        ReduceOps.AccumulatingSink<Integer, OptionalInt, ?> sink = op.makeSink();

        sink.begin(1);
        sink.accept(42);

        OptionalInt result = sink.get();
        if (!result.isPresent() || result.getAsInt() != 42) {
            throw new AssertionError("Single element failed: expected 42 but got " + result);
        }
    }

    private static void testMakeIntWithNoElements() {
        IntBinaryOperator opFunc = (a, b) -> a + b; // arbitrary
        ReduceOps.TerminalOp<Integer, OptionalInt> op = ReduceOps.makeInt(opFunc);
        ReduceOps.AccumulatingSink<Integer, OptionalInt, ?> sink = op.makeSink();

        sink.begin(0);
        // no accept calls

        OptionalInt result = sink.get();
        if (result.isPresent()) {
            throw new AssertionError("No elements failed: expected empty OptionalInt but got " + result);
        }
    }

    private static void testMakeIntWithNullOperator() {
        try {
            ReduceOps.makeInt(null);
            throw new AssertionError("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

}
