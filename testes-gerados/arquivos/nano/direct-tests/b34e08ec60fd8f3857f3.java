/*
 * @test
 * @summary Tests for ReduceOps.makeRef method to ensure correct reduction behavior.
 * @run main ReduceOpsWitupTest
 */

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.ReduceOps;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        testMakeRefWithSumOperator();
        testMakeRefWithEmptyStream();
        testMakeRefWithSingleElement();
    }

    static void testMakeRefWithSumOperator() {
        BinaryOperator<Integer> sumOperator = (a, b) -> a + b;
        var reduceOp = ReduceOps.makeRef(sumOperator);
        Optional<Integer> result = reduceOp.apply(java.util.stream.Stream.of(1, 2, 3).spliterator());
        assert result.isPresent() : "Result should be present";
        assert result.get() == 6 : "Sum of 1,2,3 should be 6";
    }

    static void testMakeRefWithEmptyStream() {
        BinaryOperator<Integer> sumOperator = (a, b) -> a + b;
        var reduceOp = ReduceOps.makeRef(sumOperator);
        Optional<Integer> result = reduceOp.apply(java.util.stream.Stream.<Integer>empty().spliterator());
        assert !result.isPresent() : "Result should be empty for empty stream";
    }

    static void testMakeRefWithSingleElement() {
        BinaryOperator<Integer> sumOperator = (a, b) -> a + b;
        var reduceOp = ReduceOps.makeRef(sumOperator);
        Optional<Integer> result = reduceOp.apply(java.util.stream.Stream.of(42).spliterator());
        assert result.isPresent() : "Result should be present";
        assert result.get() == 42 : "Single element stream should return that element";
    }
}
