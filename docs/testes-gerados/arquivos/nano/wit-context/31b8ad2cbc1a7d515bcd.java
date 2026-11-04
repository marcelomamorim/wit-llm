/*
 * @test
 * @summary Test for MethodHandles.doWhileLoop to verify behavior with null arguments.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testDoWhileLoopWithNullBody();
        testDoWhileLoopWithNullPred();
    }

    private static void testDoWhileLoopWithNullBody() {
        MethodHandle init = null;
        MethodHandle pred = null;
        try {
            MethodHandle result = MethodHandles.doWhileLoop(init, null, pred);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Expected NullPointerException for null body, but got result: " + result);
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Caught expected NullPointerException for null body.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception for null body: " + t);
            System.exit(1);
        }
    }

    private static void testDoWhileLoopWithNullPred() {
        MethodHandle init = null;
        MethodHandle pred = null;
        try {
            MethodHandle result = MethodHandles.doWhileLoop(init, MethodHandles.identity(MethodHandle.class), null);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Expected NullPointerException for null pred, but got result: " + result);
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Caught expected NullPointerException for null pred.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception for null pred: " + t);
            System.exit(1);
        }
    }
}
