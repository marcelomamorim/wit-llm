/*
 * @test
 * @summary Exercises the public Stream.reduce(BinaryOperator) path that delegates to ReduceOps.makeRef
 * @run main ReduceOpsWitupTest
 */

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        testNullOperatorThrowsNPE();
        testEmptyStreamReturnsEmpty();
        testSingleElementReturnsElement();
        testSequentialFoldOrder();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullOperatorThrowsNPE() {
        boolean thrown = false;
        try {
            Stream.of(1, 2, 3).reduce((BinaryOperator<Integer>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null operator");
    }

    static void testEmptyStreamReturnsEmpty() {
        Optional<String> result = Stream.<String>empty().reduce((a, b) -> a + b);
        check(!result.isPresent(), "empty stream reduction must be Optional.empty");
    }

    static void testSingleElementReturnsElement() {
        Optional<String> result = Stream.of("only").reduce((a, b) -> a + b);
        check(result.isPresent(), "single element reduction must be present");
        check("only".equals(result.get()), "single element must be returned unchanged");
    }

    static void testSequentialFoldOrder() {
        Optional<String> result = Stream.of("a", "b", "c").reduce((a, b) -> a + b);
        check(Optional.of("abc").equals(result), "sequential reduce must fold left to right, got " + result);
    }
}
