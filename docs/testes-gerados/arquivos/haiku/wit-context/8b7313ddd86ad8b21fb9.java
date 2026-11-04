/*
 * @test
 * @summary Checks public DoubleStream.reduce paths that reach ReduceOps.makeDouble, including null operator rejection
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.DoubleStream;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        testSumReduction();
        testEmptyStreamReturnsIdentity();
        testIdentityIsUsedAsStartValue();
        testOperatorOrderIsSequential();
        testNullOperatorThrowsNpe();
    }

    static void testSumReduction() {
        double result = DoubleStream.of(1.0, 2.0, 3.0).reduce(0.0, Double::sum);
        if (result != 6.0) {
            throw new AssertionError("expected 6.0 but was " + result);
        }
    }

    static void testEmptyStreamReturnsIdentity() {
        double result = DoubleStream.empty().reduce(5.0, Double::sum);
        if (result != 5.0) {
            throw new AssertionError("expected identity 5.0 but was " + result);
        }
    }

    static void testIdentityIsUsedAsStartValue() {
        double result = DoubleStream.of(2.0, 3.0).reduce(10.0, (a, b) -> a * b);
        if (result != 60.0) {
            throw new AssertionError("expected 60.0 but was " + result);
        }
    }

    static void testOperatorOrderIsSequential() {
        double result = DoubleStream.of(1.0, 2.0, 3.0).reduce(10.0, (a, b) -> a - b);
        if (result != 4.0) {
            throw new AssertionError("expected 4.0 but was " + result);
        }
    }

    static void testNullOperatorThrowsNpe() {
        try {
            DoubleStream.of(1.0).reduce(0.0, null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null operator");
    }
}
