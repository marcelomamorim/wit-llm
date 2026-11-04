/*
 * @test
 * @summary Witup-generated tests for DoublePipeline.reduce(double, DoubleBinaryOperator)
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testSumWithIdentity();
        testEmptyStreamReturnsIdentity();
        testMaxWithIdentity();
        testReduceOnConsumedStreamThrows();
        testReduceTwiceOnSameStreamThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSumWithIdentity() {
        double result = DoubleStream.of(1.0, 2.0, 3.0).reduce(0.0, (a, b) -> a + b);
        check(result == 6.0, "expected 6.0 but was " + result);
    }

    static void testEmptyStreamReturnsIdentity() {
        double result = DoubleStream.empty().reduce(5.0, (a, b) -> a + b);
        check(result == 5.0, "expected identity 5.0 but was " + result);
    }

    static void testMaxWithIdentity() {
        double result = DoubleStream.of(3.0, 7.0, 2.0)
                .reduce(Double.NEGATIVE_INFINITY, (a, b) -> Math.max(a, b));
        check(result == 7.0, "expected 7.0 but was " + result);
    }

    static void testReduceOnConsumedStreamThrows() {
        DoubleStream s = DoubleStream.of(1.0, 2.0);
        s.sum();
        try {
            s.reduce(0.0, (a, b) -> a + b);
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testReduceTwiceOnSameStreamThrows() {
        DoubleStream s = DoubleStream.of(1.0, 2.0);
        double first = s.reduce(0.0, (a, b) -> a + b);
        check(first == 3.0, "expected 3.0 but was " + first);
        try {
            s.reduce(0.0, (a, b) -> a + b);
            throw new RuntimeException("expected IllegalStateException on reuse");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
