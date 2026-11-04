/*
 * @test
 * @summary Test DoublePipeline.reduce(double, DoubleBinaryOperator) basic behavior
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoubleStream;
import java.util.function.DoubleBinaryOperator;

public class DoublePipelineWitupTest {
    public static void main(String[] args) {
        // Test 1: reduce sum of an empty stream returns identity
        double identity = 10.0;
        DoubleBinaryOperator op = (a, b) -> a + b;
        double result = DoubleStream.empty().reduce(identity, op);
        if (result != identity) {
            throw new AssertionError("Empty stream reduce sum should return identity");
        }

        // Test 2: reduce sum of a stream with elements
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        double expectedSum = 6.0;
        double sumResult = stream.reduce(0.0, op);
        if (sumResult != expectedSum) {
            throw new AssertionError("Sum of 1.0,2.0,3.0 should be 6.0 but was " + sumResult);
        }

        // Test 3: reduce with multiplication operator
        DoubleStream streamMul = DoubleStream.of(2.0, 3.0, 4.0);
        DoubleBinaryOperator mulOp = (a, b) -> a * b;
        double expectedMul = 24.0;
        double mulResult = streamMul.reduce(1.0, mulOp);
        if (mulResult != expectedMul) {
            throw new AssertionError("Product of 2.0,3.0,4.0 should be 24.0 but was " + mulResult);
        }

        // Test 4: reduce with identity and operator that returns identity always
        DoubleBinaryOperator identityOp = (a, b) -> identity;
        DoubleStream streamAny = DoubleStream.of(5.0, 6.0);
        double idResult = streamAny.reduce(identity, identityOp);
        if (idResult != identity) {
            throw new AssertionError("Operator always returning identity should produce identity result");
        }

        // Test 5: reduce with operator that subtracts
        DoubleStream streamSub = DoubleStream.of(10.0, 3.0, 2.0);
        DoubleBinaryOperator subOp = (a, b) -> a - b;
        double subResult = streamSub.reduce(0.0, subOp);
        // Expected: (((0 - 10) - 3) - 2) = -15
        if (subResult != -15.0) {
            throw new AssertionError("Subtraction reduce result expected -15.0 but was " + subResult);
        }
    }
}
