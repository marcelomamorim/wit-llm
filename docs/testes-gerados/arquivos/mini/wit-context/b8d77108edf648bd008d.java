/*
 * @test
 * @summary Deterministic tests for MethodHandles.whileLoop to check null argument handling and basic behavior
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testNullPredThrowsNPE();
        testNullBodyThrowsNPE();
        testWhileLoopBasicBehavior();
        System.out.println("All tests passed.");
    }

    private static void testNullPredThrowsNPE() {
        MethodHandle init = MethodHandles.identity(int.class);
        MethodHandle pred = null;
        MethodHandle body = MethodHandles.identity(int.class);
        try {
            MethodHandles.whileLoop(init, pred, body);
            throw new AssertionError("Expected NullPointerException for null pred");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullBodyThrowsNPE() {
        MethodHandle init = MethodHandles.identity(int.class);
        MethodHandle pred = MethodHandles.identity(boolean.class);
        MethodHandle body = null;
        try {
            MethodHandles.whileLoop(init, pred, body);
            throw new AssertionError("Expected NullPointerException for null body when pred is non-null");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testWhileLoopBasicBehavior() throws Throwable {
        // Create a simple while loop that counts down from 3 to 0
        // init: int i = 3
        // pred: i > 0
        // body: i = i - 1

        MethodHandle init = MethodHandles.constant(int.class, 3);
        MethodHandle pred = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "greaterThanZero", MethodType.methodType(boolean.class, int.class));
        MethodHandle body = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "decrement", MethodType.methodType(int.class, int.class));

        MethodHandle loop = MethodHandles.whileLoop(init, pred, body);

        Object result = loop.invoke();

        if (!(result instanceof Integer)) {
            throw new AssertionError("Expected Integer result from whileLoop");
        }

        int finalValue = (Integer) result;

        if (finalValue != 0) {
            throw new AssertionError("Expected final value 0 but got " + finalValue);
        }
    }

    // Helper method for pred: returns true if x > 0
    public static boolean greaterThanZero(int x) {
        return x > 0;
    }

    // Helper method for body: returns x - 1
    public static int decrement(int x) {
        return x - 1;
    }

}
