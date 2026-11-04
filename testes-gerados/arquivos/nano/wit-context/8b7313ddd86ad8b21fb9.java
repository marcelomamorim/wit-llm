/*
 * @test
 * @summary Tests for java.util.stream.ReduceOps class methods
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.ReduceOps;
import java.util.function.DoubleBinaryOperator;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        testMakeDouble_NullOperator();
        testMakeDouble_ValidOperator();
    }

    private static void testMakeDouble_NullOperator() {
        try {
            ReduceOps.makeDouble(0.0, null);
            System.err.println("Failed: Expected NullPointerException for null operator");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testMakeDouble_ValidOperator() {
        DoubleBinaryOperator addOperator = (a, b) -> a + b;
        try {
            Object result = ReduceOps.makeDouble(10.0, addOperator);
            if (result != null) {
                System.out.println("Passed: makeDouble with valid operator returns non-null");
            } else {
                System.err.println("Failed: makeDouble returned null with valid operator");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
