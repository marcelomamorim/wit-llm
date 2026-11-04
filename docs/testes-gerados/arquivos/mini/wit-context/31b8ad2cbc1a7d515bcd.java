/*
 * @test
 * @summary Deterministic tests for MethodHandles.doWhileLoop to check null argument handling and basic behavior
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testNullBodyThrowsNPE();
        testNullPredThrowsNPE();
        testDoWhileLoopBasicBehavior();
    }

    static void testNullBodyThrowsNPE() {
        MethodHandle init = MethodHandles.constant(int.class, 0);
        MethodHandle body = null;
        MethodHandle pred = MethodHandles.constant(boolean.class, false);
        try {
            MethodHandles.doWhileLoop(init, body, pred);
            throw new AssertionError("Expected NullPointerException for null body");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullPredThrowsNPE() {
        MethodHandle init = MethodHandles.constant(int.class, 0);
        MethodHandle body = MethodHandles.constant(int.class, 1);
        MethodHandle pred = null;
        try {
            MethodHandles.doWhileLoop(init, body, pred);
            throw new AssertionError("Expected NullPointerException for null pred");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testDoWhileLoopBasicBehavior() throws Throwable {
        // Create init MH returning Integer 0
        MethodHandle init = MethodHandles.constant(int.class, 0);
        // Create body MH that adds 1 to int argument
        MethodHandle body = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "increment", MethodType.methodType(int.class, int.class));
        // Create pred MH that returns true if int argument < 3
        MethodHandle pred = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "lessThanThree", MethodType.methodType(boolean.class, int.class));

        MethodHandle loop = MethodHandles.doWhileLoop(init, body, pred);

        // The loop should run: init=0, body increments by 1 while pred < 3
        // So expected final result is 3
        Object result = loop.invoke();
        if (!(result instanceof Integer)) {
            throw new AssertionError("Expected Integer result, got " + result);
        }
        int val = (Integer) result;
        if (val != 3) {
            throw new AssertionError("Expected result 3, got " + val);
        }
    }

    // Helper static methods for testDoWhileLoopBasicBehavior
    public static int increment(int x) {
        return x + 1;
    }

    public static boolean lessThanThree(int x) {
        return x < 3;
    }
}
