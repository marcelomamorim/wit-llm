/*
 * @test
 * @summary Test MethodHandles.zero(Class<?>) behavior
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Objects;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testZeroWithPrimitive();
        testZeroWithReference();
        testZeroWithNull();
    }

    private static void testZeroWithPrimitive() throws Throwable {
        // int is a primitive type
        MethodHandle mh = MethodHandles.zero(int.class);
        Objects.requireNonNull(mh);
        // invoke the method handle and expect 0 (int default)
        int result = (int) mh.invokeExact();
        if (result != 0) {
            throw new AssertionError("Expected zero int, got " + result);
        }
    }

    private static void testZeroWithReference() throws Throwable {
        // String is a reference type
        MethodHandle mh = MethodHandles.zero(String.class);
        Objects.requireNonNull(mh);
        // invoke the method handle and expect null (default for reference)
        Object result = mh.invokeExact();
        if (result != null) {
            throw new AssertionError("Expected null reference, got " + result);
        }
    }

    private static void testZeroWithNull() {
        try {
            MethodHandles.zero(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
