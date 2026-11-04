/*
 * @test
 * @summary Tests for ReduceOps.makeInt method in java.util.stream
 * @run main ReduceOpsWitupTest
 */
import java.util.stream.ReduceOps;
import java.util.function.IntBinaryOperator;

public class ReduceOpsWitupTest {
    public static void main(String[] args) {
        testMakeIntWithAddition();
        testMakeIntWithMultiplication();
        testMakeIntWithNullOperator();
    }

    static void testMakeIntWithAddition() {
        IntBinaryOperator add = (a, b) -> a + b;
        var op = ReduceOps.makeInt(0, add);
        int result = ((ReduceOps.ReduceOp<Integer, Integer, ReduceOps.ReduceIntSink>)op).makeSink().acceptAndReturn(5, 5);
        assert result == 10 : "Expected 10, got " + result;
    }

    static void testMakeIntWithMultiplication() {
        IntBinaryOperator mul = (a, b) -> a * b;
        var op = ReduceOps.makeInt(1, mul);
        int result = ((ReduceOps.ReduceOp<Integer, Integer, ReduceOps.ReduceIntSink>)op).makeSink().acceptAndReturn(3, 3);
        assert result == 9 : "Expected 9, got " + result;
    }

    static void testMakeIntWithNullOperator() {
        try {
            ReduceOps.makeInt(0, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
