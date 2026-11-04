/*
 * @test
 * @summary Deterministic public-API tests for DoublePipeline.reduce(double, DoubleBinaryOperator)
 * @run main DoublePipelineWitupTest
 */
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testSumOfElements();
        testEmptyStreamReturnsIdentity();
        testProductOfElements();
        testSingleElementCombinedWithIdentity();
        testSequentialOrderOfOperations();
        testMaxWithNegativeInfinityIdentity();
    }

    static void testSumOfElements() {
        double sum = DoubleStream.of(1.0, 2.0, 3.0).reduce(0.0, (a, b) -> a + b);
        check(sum == 6.0, "sum mismatch");
    }

    static void testEmptyStreamReturnsIdentity() {
        double result = DoubleStream.empty().reduce(5.0, (a, b) -> a * b);
        check(result == 5.0, "empty stream must return identity");
    }

    static void testProductOfElements() {
        double product = DoubleStream.of(2.0, 3.0, 4.0).reduce(1.0, (a, b) -> a * b);
        check(product == 24.0, "product mismatch");
    }

    static void testSingleElementCombinedWithIdentity() {
        double result = DoubleStream.of(7.5).reduce(0.5, (a, b) -> a + b);
        check(result == 8.0, "single element reduce mismatch");
    }

    static void testSequentialOrderOfOperations() {
        double result = DoubleStream.of(1.0, 2.0, 3.0).reduce(0.0, (a, b) -> a - b);
        check(result == -6.0, "left-to-right order mismatch");
    }

    static void testMaxWithNegativeInfinityIdentity() {
        double result = DoubleStream.of(-1.0, -5.0).reduce(Double.NEGATIVE_INFINITY, (a, b) -> Math.max(a, b));
        check(result == -1.0, "max reduce mismatch");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
