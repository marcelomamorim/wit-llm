/*
 * @test
 * @summary Tests MethodHandles.zero(Class) null validation and the type and zero value of returned handles
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testNullType();
        testIntZero();
        testLongZero();
        testObjectZero();
        testStringZero();
    }

    static void testNullType() {
        try {
            MethodHandles.zero(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null type");
    }

    static void testIntZero() throws Throwable {
        MethodHandle h = MethodHandles.zero(int.class);
        if (!h.type().equals(MethodType.methodType(int.class))) {
            throw new RuntimeException("Unexpected type for int zero handle: " + h.type());
        }
        int v = (int) h.invokeExact();
        if (v != 0) {
            throw new RuntimeException("Expected 0 but got " + v);
        }
    }

    static void testLongZero() throws Throwable {
        MethodHandle h = MethodHandles.zero(long.class);
        if (!h.type().equals(MethodType.methodType(long.class))) {
            throw new RuntimeException("Unexpected type for long zero handle: " + h.type());
        }
        long v = (long) h.invokeExact();
        if (v != 0L) {
            throw new RuntimeException("Expected 0L but got " + v);
        }
    }

    static void testObjectZero() throws Throwable {
        MethodHandle h = MethodHandles.zero(Object.class);
        if (!h.type().equals(MethodType.methodType(Object.class))) {
            throw new RuntimeException("Unexpected type for Object zero handle: " + h.type());
        }
        Object o = (Object) h.invokeExact();
        if (o != null) {
            throw new RuntimeException("Expected null for Object zero handle");
        }
    }

    static void testStringZero() throws Throwable {
        MethodHandle h = MethodHandles.zero(String.class);
        if (!h.type().equals(MethodType.methodType(String.class))) {
            throw new RuntimeException("Unexpected type for String zero handle: " + h.type());
        }
        String s = (String) h.invokeExact();
        if (s != null) {
            throw new RuntimeException("Expected null for String zero handle");
        }
    }
}
