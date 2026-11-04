/*
 * @test
 * @summary Test for java.util.stream.ReduceOps.makeRef(BinaryOperator<T>)
 * @run main ReduceOpsWitupTest
 */

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.ReduceOps;

public class ReduceOpsWitupTest {
    public static void main(String[] args) {
        testMakeRefNullOperator();
        testMakeRefEmptyStream();
        testMakeRefSingleElement();
        testMakeRefMultipleElements();
    }

    static void testMakeRefNullOperator() {
        try {
            ReduceOps.makeRef(null);
            throw new AssertionError("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    static void testMakeRefEmptyStream() {
        BinaryOperator<String> op = (a, b) -> a + b;
        ReduceOps.TerminalOp<String, Optional<String>> terminalOp = ReduceOps.makeRef(op);
        ReduceOps.AccumulatingSink<String, Optional<String>, ?> sink = terminalOp.makeSink();
        sink.begin(0L);
        // no accept calls, simulating empty stream
        Optional<String> result = sink.get();
        if (result.isPresent()) {
            throw new AssertionError("Expected Optional.empty() for empty input");
        }
    }

    static void testMakeRefSingleElement() {
        BinaryOperator<Integer> op = (a, b) -> a + b;
        ReduceOps.TerminalOp<Integer, Optional<Integer>> terminalOp = ReduceOps.makeRef(op);
        ReduceOps.AccumulatingSink<Integer, Optional<Integer>, ?> sink = terminalOp.makeSink();
        sink.begin(1L);
        sink.accept(42);
        Optional<Integer> result = sink.get();
        if (!result.isPresent() || result.get() != 42) {
            throw new AssertionError("Expected Optional.of(42) for single element input");
        }
    }

    static void testMakeRefMultipleElements() {
        BinaryOperator<String> op = (a, b) -> a + b;
        ReduceOps.TerminalOp<String, Optional<String>> terminalOp = ReduceOps.makeRef(op);
        ReduceOps.AccumulatingSink<String, Optional<String>, ?> sink = terminalOp.makeSink();
        sink.begin(3L);
        sink.accept("a");
        sink.accept("b");
        sink.accept("c");
        Optional<String> result = sink.get();
        if (!result.isPresent() || !"abc".equals(result.get())) {
            throw new AssertionError("Expected Optional.of(\"abc\") for concatenated input");
        }
    }
}
