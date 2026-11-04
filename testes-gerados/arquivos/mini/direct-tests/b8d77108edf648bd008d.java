/*
 * @test
 * @summary Test MethodHandles.whileLoop with simple MethodHandles
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        // Create a simple init MethodHandle returning Integer 0
        MethodHandle init = MethodHandles.constant(int.class, 0);

        // Create a predicate MethodHandle: (int) -> boolean, returns true if arg < 3
        MethodHandle pred = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "lessThan3", MethodType.methodType(boolean.class, int.class));

        // Create a body MethodHandle: (int) -> int, returns arg + 1
        MethodHandle body = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "increment", MethodType.methodType(int.class, int.class));

        // Compose whileLoop
        MethodHandle loop = MethodHandles.whileLoop(init, pred, body);

        // Invoke the loop, expect final result 3
        Object result = loop.invoke();
        if (!(result instanceof Integer)) {
            throw new AssertionError("Result is not Integer");
        }
        int intResult = (Integer) result;
        if (intResult != 3) {
            throw new AssertionError("Expected 3 but got " + intResult);
        }

        // Test with void return type body
        MethodHandle initVoid = MethodHandles.constant(int.class, 0);
        MethodHandle predVoid = pred;
        MethodHandle bodyVoid = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "sideEffectBody", MethodType.methodType(void.class, int.class));

        MethodHandle loopVoid = MethodHandles.whileLoop(initVoid, predVoid, bodyVoid);

        // Invoke loopVoid, expect null (void return)
        Object voidResult = loopVoid.invoke();
        if (voidResult != null) {
            throw new AssertionError("Expected null for void return type but got " + voidResult);
        }
    }

    public static boolean lessThan3(int x) {
        return x < 3;
    }

    public static int increment(int x) {
        return x + 1;
    }

    public static void sideEffectBody(int x) {
        // no-op
    }
}
