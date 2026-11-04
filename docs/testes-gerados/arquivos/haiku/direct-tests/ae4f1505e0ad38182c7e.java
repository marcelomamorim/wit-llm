/*
 * @test
 * @summary Basic behavior of MethodHandles.zero(Class)
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        checkPrimitive(int.class, Integer.valueOf(0));
        checkPrimitive(long.class, Long.valueOf(0L));
        checkPrimitive(boolean.class, Boolean.FALSE);
        checkPrimitive(double.class, Double.valueOf(0.0d));
        checkReference(String.class);
        checkReference(Object.class);
        checkNullRejected();
    }

    static void checkPrimitive(Class<?> c, Object expected) throws Throwable {
        MethodHandle mh = MethodHandles.zero(c);
        if (!mh.type().equals(MethodType.methodType(c))) {
            throw new RuntimeException("type mismatch for " + c);
        }
        Object result = mh.invoke();
        if (!expected.equals(result)) {
            throw new RuntimeException("unexpected zero value for " + c + ": " + result);
        }
    }

    static void checkReference(Class<?> c) throws Throwable {
        MethodHandle mh = MethodHandles.zero(c);
        if (!mh.type().equals(MethodType.methodType(c))) {
            throw new RuntimeException("type mismatch for " + c);
        }
        Object result = mh.invoke();
        if (result != null) {
            throw new RuntimeException("expected null for " + c + " but got " + result);
        }
    }

    static void checkNullRejected() {
        try {
            MethodHandles.zero(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null type");
    }
}
