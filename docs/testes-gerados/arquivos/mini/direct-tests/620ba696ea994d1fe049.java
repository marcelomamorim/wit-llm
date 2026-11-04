/*
 * @test
 * @summary Test MethodHandles.empty with various MethodType inputs
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testEmptyWithVoidReturnType();
        testEmptyWithPrimitiveReturnType();
        testEmptyWithObjectReturnType();
        testEmptyWithParameters();
        testEmptyRejectsNull();
    }

    private static void testEmptyWithVoidReturnType() throws Throwable {
        MethodType mt = MethodType.methodType(void.class);
        MethodHandle mh = MethodHandles.empty(mt);
        Objects.requireNonNull(mh);
        // The method handle should accept no arguments and return void
        mh.invokeExact();
    }

    private static void testEmptyWithPrimitiveReturnType() throws Throwable {
        MethodType mt = MethodType.methodType(int.class);
        MethodHandle mh = MethodHandles.empty(mt);
        Objects.requireNonNull(mh);
        // The method handle should accept no arguments and return int default 0
        int result = (int) mh.invokeExact();
        if (result != 0) throw new AssertionError("Expected 0 but got " + result);
    }

    private static void testEmptyWithObjectReturnType() throws Throwable {
        MethodType mt = MethodType.methodType(String.class);
        MethodHandle mh = MethodHandles.empty(mt);
        Objects.requireNonNull(mh);
        // The method handle should accept no arguments and return null
        Object result = mh.invokeExact();
        if (result != null) throw new AssertionError("Expected null but got " + result);
    }

    private static void testEmptyWithParameters() throws Throwable {
        MethodType mt = MethodType.methodType(void.class, int.class, String.class);
        MethodHandle mh = MethodHandles.empty(mt);
        Objects.requireNonNull(mh);
        // The method handle should accept two arguments and return void
        mh.invokeExact(42, "test");
    }

    private static void testEmptyRejectsNull() {
        try {
            MethodHandles.empty(null);
            throw new AssertionError("Expected NullPointerException for null MethodType");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
