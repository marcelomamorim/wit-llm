/*
 * @test
 * @summary Verifies MethodHandles.empty(MethodType) null rejection, return values and parameter types
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testNullTypeThrowsNullPointerException();
        testIntReturnYieldsZero();
        testVoidReturnYieldsNull();
        testObjectReturnYieldsNullAndKeepsParameterTypes();
    }

    static void testNullTypeThrowsNullPointerException() {
        boolean thrown = false;
        try {
            MethodHandles.empty(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null MethodType");
        }
    }

    static void testIntReturnYieldsZero() throws Throwable {
        MethodType expected = MethodType.methodType(int.class, String.class);
        MethodHandle mh = MethodHandles.empty(expected);
        if (!mh.type().equals(expected)) {
            throw new RuntimeException("Unexpected type: " + mh.type());
        }
        Object result = mh.invokeWithArguments("a");
        if (!Integer.valueOf(0).equals(result)) {
            throw new RuntimeException("Expected Integer 0 but was " + result);
        }
    }

    static void testVoidReturnYieldsNull() throws Throwable {
        MethodType expected = MethodType.methodType(void.class);
        MethodHandle mh = MethodHandles.empty(expected);
        if (!mh.type().equals(expected)) {
            throw new RuntimeException("Unexpected type: " + mh.type());
        }
        Object result = mh.invokeWithArguments();
        if (result != null) {
            throw new RuntimeException("Expected null for void return but was " + result);
        }
    }

    static void testObjectReturnYieldsNullAndKeepsParameterTypes() throws Throwable {
        MethodType expected = MethodType.methodType(String.class, int.class, long.class);
        MethodHandle mh = MethodHandles.empty(expected);
        if (!mh.type().equals(expected)) {
            throw new RuntimeException("Unexpected type: " + mh.type());
        }
        Object result = mh.invokeWithArguments(1, 2L);
        if (result != null) {
            throw new RuntimeException("Expected null for String return but was " + result);
        }
    }
}
