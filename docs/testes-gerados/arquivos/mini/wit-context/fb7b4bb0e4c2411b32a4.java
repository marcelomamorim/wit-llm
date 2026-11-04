/*
 * @test
 * @summary Test MethodHandles.spreadInvoker for argument count validation and normal behavior
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testLeadingArgCountNegative();
        testLeadingArgCountTooLarge();
        testValidSpreadInvoker();
        System.out.println("All tests passed.");
    }

    private static void testLeadingArgCountNegative() {
        MethodType mt = MethodType.methodType(void.class);
        try {
            MethodHandles.spreadInvoker(mt, -1);
            throw new AssertionError("Expected IllegalArgumentException for negative leadingArgCount");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testLeadingArgCountTooLarge() {
        MethodType mt = MethodType.methodType(void.class, Object.class);
        try {
            MethodHandles.spreadInvoker(mt, 2); // leadingArgCount > parameterCount
            throw new AssertionError("Expected IllegalArgumentException for leadingArgCount > parameterCount");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidSpreadInvoker() {
        MethodType mt = MethodType.methodType(void.class, Object.class, Object.class);
        int leadingArgCount = 1;
        MethodHandle mh = MethodHandles.spreadInvoker(mt, leadingArgCount);
        if (mh == null) {
            throw new AssertionError("spreadInvoker returned null for valid input");
        }
    }
}
