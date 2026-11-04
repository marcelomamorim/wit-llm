/*
 * @test
 * @summary Test MethodHandles.doWhileLoop with valid and invalid inputs
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testDoWhileLoopValid();
        testDoWhileLoopInvalid();
    }

    private static void testDoWhileLoopValid() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // init: ()int returning 0
        MethodHandle init = MethodHandles.constant(int.class, 0);

        // body: (int)int increment
        MethodHandle body = lookup.findStatic(MethodHandlesWitupTest.class, "increment", MethodType.methodType(int.class, int.class));

        // pred: (int)boolean returns true if less than 3
        MethodHandle pred = lookup.findStatic(MethodHandlesWitupTest.class, "lessThanThree", MethodType.methodType(boolean.class, int.class));

        MethodHandle loop = MethodHandles.doWhileLoop(init, body, pred);

        // invoke loop, expect 3
        int result = (int) loop.invokeExact();
        if (result != 3) {
            throw new AssertionError("Expected 3 but got " + result);
        }
    }

    private static void testDoWhileLoopInvalid() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        MethodHandle init = MethodHandles.constant(int.class, 0);
        MethodHandle body = MethodHandles.constant(int.class, 1);
        MethodHandle pred = MethodHandles.constant(boolean.class, true);

        // body has no parameters, pred has no parameters, should cause IllegalArgumentException
        try {
            MethodHandles.doWhileLoop(init, body, pred);
            throw new AssertionError("Expected IllegalArgumentException for invalid method types");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    // Helper methods for valid test
    public static int increment(int x) {
        return x + 1;
    }

    public static boolean lessThanThree(int x) {
        return x < 3;
    }
}
