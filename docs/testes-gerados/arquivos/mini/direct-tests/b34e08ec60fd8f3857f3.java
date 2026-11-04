/*
 * @test
 * @summary Test java.util.stream.ReduceOps.makeRef(BinaryOperator) behavior
 * @run main ReduceOpsWitupTest
 */

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        testEmptyStreamReturnsEmptyOptional();
        testSingleElementStreamReturnsElement();
        testMultipleElementsStreamReducesCorrectly();
        testNullOperatorThrowsNPE();
    }

    static void testEmptyStreamReturnsEmptyOptional() {
        BinaryOperator<String> op = (a, b) -> a + b;
        java.util.stream.TerminalOp<String, Optional<String>> reduceOp = java.util.stream.ReduceOps.makeRef(op);

        Stream<String> emptyStream = Stream.empty();
        Optional<String> result = emptyStream.collect(reduceOp);

        if (result.isPresent()) {
            throw new AssertionError("Expected empty Optional for empty stream");
        }
    }

    static void testSingleElementStreamReturnsElement() {
        BinaryOperator<String> op = (a, b) -> a + b;
        java.util.stream.TerminalOp<String, Optional<String>> reduceOp = java.util.stream.ReduceOps.makeRef(op);

        Stream<String> singleStream = Stream.of("x");
        Optional<String> result = singleStream.collect(reduceOp);

        if (!result.isPresent() || !"x".equals(result.get())) {
            throw new AssertionError("Expected Optional containing 'x' for single element stream");
        }
    }

    static void testMultipleElementsStreamReducesCorrectly() {
        BinaryOperator<String> op = (a, b) -> a + b;
        java.util.stream.TerminalOp<String, Optional<String>> reduceOp = java.util.stream.ReduceOps.makeRef(op);

        Stream<String> stream = Stream.of("a", "b", "c");
        Optional<String> result = stream.collect(reduceOp);

        if (!result.isPresent() || !"abc".equals(result.get())) {
            throw new AssertionError("Expected Optional containing 'abc' for stream of 'a','b','c'");
        }
    }

    static void testNullOperatorThrowsNPE() {
        try {
            java.util.stream.ReduceOps.makeRef(null);
            throw new AssertionError("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
