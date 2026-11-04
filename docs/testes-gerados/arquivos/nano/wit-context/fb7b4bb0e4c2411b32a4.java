/*
 * @test
 * @summary Test spreadInvoker method for valid and invalid leadingArgCount values.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testSpreadInvokerValid();
        testSpreadInvokerInvalidNegative();
        testSpreadInvokerInvalidTooLarge();
    }

    static void testSpreadInvokerValid() {
        try {
            MethodType type = MethodType.methodType(void.class, String.class, Integer.class);
            int leadingArgCount = 1;
            MethodHandle mh = MethodHandles.spreadInvoker(type, leadingArgCount);
            if (mh == null) {
                throw new AssertionError("spreadInvoker returned null for valid input");
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in valid spreadInvoker test", t);
        }
    }

    static void testSpreadInvokerInvalidNegative() {
        try {
            MethodType type = MethodType.methodType(void.class, String.class);
            int leadingArgCount = -1;
            MethodHandles.spreadInvoker(type, leadingArgCount);
            throw new AssertionError("Expected IllegalArgumentException for negative leadingArgCount");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type for negative leadingArgCount", t);
        }
    }

    static void testSpreadInvokerInvalidTooLarge() {
        try {
            MethodType type = MethodType.methodType(void.class, String.class);
            int leadingArgCount = 2; // greater than parameter count
            MethodHandles.spreadInvoker(type, leadingArgCount);
            throw new AssertionError("Expected IllegalArgumentException for too large leadingArgCount");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type for too large leadingArgCount", t);
        }
    }
}
