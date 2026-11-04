/*
 * @test
 * @summary Test Unsafe.allocateUninitializedArray method for argument validation and normal behavior
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test null componentType throws IllegalArgumentException
        try {
            unsafe.allocateUninitializedArray(null, 1);
            throw new Error("Expected IllegalArgumentException for null componentType");
        } catch (IllegalArgumentException e) {
            if (!"Component type is null".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }

        // Test non-primitive componentType throws IllegalArgumentException
        try {
            unsafe.allocateUninitializedArray(String.class, 1);
            throw new Error("Expected IllegalArgumentException for non-primitive componentType");
        } catch (IllegalArgumentException e) {
            if (!"Component type is not primitive".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }

        // Test negative length throws IllegalArgumentException
        try {
            unsafe.allocateUninitializedArray(int.class, -1);
            throw new Error("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            if (!"Negative length".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }

        // Test valid primitive componentType and zero length returns non-null
        Object arr = unsafe.allocateUninitializedArray(int.class, 0);
        if (arr == null) {
            throw new Error("Returned array is null for zero length");
        }
        if (!arr.getClass().isArray()) {
            throw new Error("Returned object is not an array");
        }

        // Test valid primitive componentType and positive length returns array of correct length
        int length = 5;
        Object arr2 = unsafe.allocateUninitializedArray(int.class, length);
        if (arr2 == null) {
            throw new Error("Returned array is null for positive length");
        }
        if (!arr2.getClass().isArray()) {
            throw new Error("Returned object is not an array");
        }
        if (java.lang.reflect.Array.getLength(arr2) != length) {
            throw new Error("Returned array length mismatch: expected " + length + " but got " + java.lang.reflect.Array.getLength(arr2));
        }
    }
}
