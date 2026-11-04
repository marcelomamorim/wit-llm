/*
 * @test
 * @summary Tests for MethodHandles.whileLoop to verify behavior with null arguments.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testWhileLoopWithNullPred();
        testWhileLoopWithNullBody();
    }

    private static void testWhileLoopWithNullPred() {
        MethodHandle init = null;
        MethodHandle pred = null;
        MethodHandle body = null;
        try {
            MethodHandles.whileLoop(init, pred, body);
            System.out.println("FAIL: Expected NullPointerException for null pred");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null pred");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null pred: " + t);
        }
    }

    private static void testWhileLoopWithNullBody() {
        MethodHandle init = null;
        MethodHandle pred = null;
        MethodHandle body = null;
        try {
            MethodHandles.whileLoop(init, pred, body);
            System.out.println("FAIL: Expected NullPointerException for null body");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null body");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null body: " + t);
        }
    }
}
