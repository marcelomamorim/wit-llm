/*
 * @test
 * @summary Test MethodHandles.iteratedLoop for null argument and basic behavior
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testNullBodyThrowsNPE();
        testValidIteratedLoopBasic();
    }

    static void testNullBodyThrowsNPE() {
        MethodHandle iterator = null;
        MethodHandle init = null;
        MethodHandle body = null;
        try {
            MethodHandles.iteratedLoop(iterator, init, body);
            throw new Error("Expected NullPointerException for null body");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testValidIteratedLoopBasic() throws Throwable {
        // Provide minimal valid MethodHandles for iterator, init and body
        // iterator: a MethodHandle that returns an Iterator (simulate with identity)
        MethodHandle iterator = MethodHandles.identity(java.util.Iterator.class);

        // init: a MethodHandle that returns initial value (Integer 0)
        MethodHandle init = MethodHandles.constant(int.class, 0);

        // body: a MethodHandle that takes (Integer state, Object element) and returns Integer
        // For simplicity, use a MethodHandle that returns the first argument (state) unchanged
        MethodHandle body = MethodHandles.identity(int.class);

        MethodHandle loop = MethodHandles.iteratedLoop(iterator, init, body);
        if (loop == null) {
            throw new Error("iteratedLoop returned null");
        }
        // Check that the returned MethodHandle type is consistent
        Class<?> rt = loop.type().returnType();
        if (rt == void.class) {
            throw new Error("iteratedLoop returned MethodHandle with void return type unexpectedly");
        }
    }
}
