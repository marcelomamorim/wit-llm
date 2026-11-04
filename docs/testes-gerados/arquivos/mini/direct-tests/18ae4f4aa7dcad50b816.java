/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.allocateUninitializedArray method
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test null componentType throws IllegalArgumentException
        try {
            unsafe.allocateUninitializedArray(null, 1);
            throw new RuntimeException("Expected IllegalArgumentException for null componentType");
        } catch (IllegalArgumentException e) {
            if (!"Component type is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for null componentType: " + e.getMessage());
            }
        }

        // Test non-primitive componentType throws IllegalArgumentException
        try {
            unsafe.allocateUninitializedArray(String.class, 1);
            throw new RuntimeException("Expected IllegalArgumentException for non-primitive componentType");
        } catch (IllegalArgumentException e) {
            if (!"Component type is not primitive".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for non-primitive componentType: " + e.getMessage());
            }
        }

        // Test negative length throws IllegalArgumentException
        try {
            unsafe.allocateUninitializedArray(int.class, -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            if (!"Negative length".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for negative length: " + e.getMessage());
            }
        }

        // Test valid primitive componentType and zero length returns non-null array
        Object arr = unsafe.allocateUninitializedArray(int.class, 0);
        if (arr == null) {
            throw new RuntimeException("Returned array is null for zero length");
        }
        if (!arr.getClass().isArray()) {
            throw new RuntimeException("Returned object is not an array");
        }
        if (arr.getClass().getComponentType() != int.class) {
            throw new RuntimeException("Returned array component type is not int");
        }

        // Test valid primitive componentType and positive length returns array of correct length
        int length = 5;
        Object arr2 = unsafe.allocateUninitializedArray(long.class, length);
        if (arr2 == null) {
            throw new RuntimeException("Returned array is null for positive length");
        }
        if (!arr2.getClass().isArray()) {
            throw new RuntimeException("Returned object is not an array");
        }
        if (arr2.getClass().getComponentType() != long.class) {
            throw new RuntimeException("Returned array component type is not long");
        }
        if (java.lang.reflect.Array.getLength(arr2) != length) {
            throw new RuntimeException("Returned array length mismatch");
        }

        System.out.println("All tests passed.");
    }
}
