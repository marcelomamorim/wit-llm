/*
 * @test
 * @summary Checks public DoubleStream.reduce(DoubleBinaryOperator) behavior, which delegates to DoublePipeline.reduce
 * @run main DoublePipelineWitupTest
 */

import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testSumReduction();
        testSingleElementReduction();
        testEmptyStreamReturnsEmpty();
        testMaxReduction();
        testOrderedNonCommutativeReduction();
    }

    static void testSumReduction() {
        OptionalDouble result = DoubleStream.of(1.0, 2.0, 3.0).reduce((a, b) -> a + b);
        check(result.isPresent(), "sum result should be present");
        check(result.getAsDouble() == 6.0, "expected 6.0 but was " + result.getAsDouble());
    }

    static void testSingleElementReduction() {
        OptionalDouble result = DoubleStream.of(42.5).reduce((a, b) -> a + b);
        check(result.isPresent(), "single element result should be present");
        check(result.getAsDouble() == 42.5, "expected 42.5 but was " + result.getAsDouble());
    }

    static void testEmptyStreamReturnsEmpty() {
        OptionalDouble result = DoubleStream.empty().reduce((a, b) -> a + b);
        check(!result.isPresent(), "empty stream reduction should be empty");
    }

    static void testMaxReduction() {
        OptionalDouble result = DoubleStream.of(3.0, -7.0, 9.5, 2.0).reduce(Math::max);
        check(result.isPresent(), "max result should be present");
        check(result.getAsDouble() == 9.5, "expected 9.5 but was " + result.getAsDouble());
    }

    static void testOrderedNonCommutativeReduction() {
        // Subtraction is non-commutative: a left-to-right fold of 10, 2, 3 gives (10-2)-3 = 5.
        OptionalDouble result = DoubleStream.of(10.0, 2.0, 3.0).reduce((a, b) -> a - b);
        check(result.isPresent(), "ordered result should be present");
        check(result.getAsDouble() == 5.0, "expected 5.0 but was " + result.getAsDouble());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
