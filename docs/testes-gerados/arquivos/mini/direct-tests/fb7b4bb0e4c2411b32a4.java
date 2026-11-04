/*
 * @test
 * @summary Test MethodHandles.spreadInvoker for valid and invalid leadingArgCount
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testValidLeadingArgCount();
        testLeadingArgCountNegative();
        testLeadingArgCountTooLarge();
    }

    static void testValidLeadingArgCount() throws Throwable {
        MethodType mt = MethodType.methodType(String.class, int.class, double.class, boolean.class);
        int leadingArgCount = 2;
        MethodHandle mh = new MethodHandles().spreadInvoker(mt, leadingArgCount);
        if (mh == null) {
            throw new AssertionError("spreadInvoker returned null for valid input");
        }
    }

    static void testLeadingArgCountNegative() {
        MethodType mt = MethodType.methodType(void.class);
        int leadingArgCount = -1;
        try {
            new MethodHandles().spreadInvoker(mt, leadingArgCount);
            throw new AssertionError("Expected IllegalArgumentException for negative leadingArgCount");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testLeadingArgCountTooLarge() {
        MethodType mt = MethodType.methodType(void.class, int.class);
        int leadingArgCount = 2; // greater than parameterCount()
        try {
            new MethodHandles().spreadInvoker(mt, leadingArgCount);
            throw new AssertionError("Expected IllegalArgumentException for leadingArgCount > parameterCount");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
