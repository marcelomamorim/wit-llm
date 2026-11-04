/*
 * @test
 * @summary Test MethodHandles.empty(MethodType) for null argument and basic behavior
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testEmptyWithNull();
        testEmptyWithValidType();
    }

    private static void testEmptyWithNull() {
        try {
            MethodHandles.empty(null);
            throw new Error("Expected NullPointerException for null MethodType");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyWithValidType() throws Throwable {
        MethodType mt = MethodType.methodType(void.class);
        MethodHandle mh = MethodHandles.empty(mt);
        if (mh == null) {
            throw new Error("MethodHandles.empty returned null");
        }
        // The returned MethodHandle should have the same return type as mt
        if (!mh.type().returnType().equals(void.class)) {
            throw new Error("Returned MethodHandle has unexpected return type");
        }
        // The returned MethodHandle should accept the parameters of mt (none here)
        if (mh.type().parameterCount() != 0) {
            throw new Error("Returned MethodHandle has unexpected parameter count");
        }
    }
}
