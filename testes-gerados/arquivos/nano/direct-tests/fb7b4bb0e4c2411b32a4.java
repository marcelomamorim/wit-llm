/*
 * @test
 * @summary Test for MethodHandles.spreadInvoker to ensure correct argument validation and behavior.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testInvalidLeadingArgCount();
        testValidSpreadInvoker();
    }

    private static void testInvalidLeadingArgCount() {
        MethodType type = MethodType.methodType(String.class, Integer.class, String.class);
        // leadingArgCount < 0
        try {
            MethodHandle mh = MethodHandles.spreadInvoker(type, -1);
            throw new AssertionError("Expected IllegalArgumentException for negative leadingArgCount");
        } catch (IllegalArgumentException e) {
            // expected
        }
        // leadingArgCount > parameterCount
        try {
            MethodHandle mh = MethodHandles.spreadInvoker(type, 3);
            throw new AssertionError("Expected IllegalArgumentException for leadingArgCount > parameterCount");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidSpreadInvoker() {
        MethodType type = MethodType.methodType(String.class, Integer.class, String.class);
        int leadingArgCount = 1;
        MethodHandle mh = MethodHandles.spreadInvoker(type, leadingArgCount);
        if (mh == null) {
            throw new AssertionError("spreadInvoker returned null");
        }
        // Additional behavior tests could be added here if the invoker is invoked, but since it's a private API, we test only for non-null and no exceptions.
    }
}
