/*
 * @test
 * @summary Verify MethodHandles.empty returns handles of the requested type that yield zero values
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testTypeIsPreserved();
        testIntReturnYieldsZero();
        testObjectReturnYieldsNull();
        testVoidReturnYieldsNull();
        testNullTypeThrowsNPE();
    }

    static void testTypeIsPreserved() {
        MethodType mt = MethodType.methodType(int.class, String.class, long.class);
        MethodHandle mh = MethodHandles.empty(mt);
        if (!mh.type().equals(mt)) {
            throw new RuntimeException("type mismatch: " + mh.type());
        }
    }

    static void testIntReturnYieldsZero() throws Throwable {
        MethodHandle mh = MethodHandles.empty(MethodType.methodType(int.class, String.class));
        Object result = mh.invokeWithArguments("x");
        if (!Integer.valueOf(0).equals(result)) {
            throw new RuntimeException("expected 0 but got " + result);
        }
    }

    static void testObjectReturnYieldsNull() throws Throwable {
        MethodHandle mh = MethodHandles.empty(MethodType.methodType(String.class, int.class));
        Object result = mh.invokeWithArguments(42);
        if (result != null) {
            throw new RuntimeException("expected null but got " + result);
        }
    }

    static void testVoidReturnYieldsNull() throws Throwable {
        MethodHandle mh = MethodHandles.empty(MethodType.methodType(void.class, int.class));
        Object result = mh.invokeWithArguments(1);
        if (result != null) {
            throw new RuntimeException("expected null for void but got " + result);
        }
    }

    static void testNullTypeThrowsNPE() {
        try {
            MethodHandles.empty(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null type");
    }
}
