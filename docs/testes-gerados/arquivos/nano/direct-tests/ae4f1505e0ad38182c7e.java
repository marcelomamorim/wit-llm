/*
 * @test
 * @summary Test MethodHandles.zero(Class<?>) for primitive and object types
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testZeroWithPrimitive();
        testZeroWithObject();
    }

    static void testZeroWithPrimitive() {
        try {
            Class<?> primitiveType = int.class;
            MethodHandle mh = MethodHandles.zero(primitiveType);
            Objects.requireNonNull(mh, "MethodHandle should not be null for primitive type");
            // Verify that the returned MethodHandle is of expected type
            assert mh.type().equals(MethodType.methodType(primitiveType)) : "Type mismatch for primitive zero";
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for primitive type", t);
        }
    }

    static void testZeroWithObject() {
        try {
            Class<?> objectType = String.class;
            MethodHandle mh = MethodHandles.zero(objectType);
            Objects.requireNonNull(mh, "MethodHandle should not be null for object type");
            // Verify that the returned MethodHandle is of expected type
            assert mh.type().equals(MethodType.methodType(objectType)) : "Type mismatch for object zero";
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for object type", t);
        }
    }
}
