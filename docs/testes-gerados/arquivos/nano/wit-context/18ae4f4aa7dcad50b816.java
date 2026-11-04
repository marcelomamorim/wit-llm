/*
 * @test
 * @summary Test for Unsafe.allocateUninitializedArray method, focusing on exception path when componentType is null.
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testAllocateUninitializedArrayNullComponentType();
        testAllocateUninitializedArrayNonPrimitiveComponentType();
        testAllocateUninitializedArrayNegativeLength();
        testAllocateUninitializedArrayValid();
    }

    private static void testAllocateUninitializedArrayNullComponentType() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            unsafe.allocateUninitializedArray(null, 10);
            System.err.println("Failed: Expected IllegalArgumentException for null componentType");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for null componentType");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testAllocateUninitializedArrayNonPrimitiveComponentType() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            Class<?> nonPrimitiveClass = String.class;
            unsafe.allocateUninitializedArray(nonPrimitiveClass, 10);
            System.err.println("Failed: Expected IllegalArgumentException for non-primitive componentType");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for non-primitive componentType");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testAllocateUninitializedArrayNegativeLength() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            Class<?> primitiveType = int.class;
            unsafe.allocateUninitializedArray(primitiveType, -1);
            System.err.println("Failed: Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for negative length");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testAllocateUninitializedArrayValid() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            Class<?> primitiveType = int.class;
            Object array = unsafe.allocateUninitializedArray(primitiveType, 5);
            if (array != null && array.getClass().isArray() && array.getClass().getComponentType() == int.class && java.lang.reflect.Array.getLength(array) == 5) {
                System.out.println("Passed: Successfully allocated array of int with length 5");
            } else {
                System.err.println("Failed: Array allocation did not produce expected array");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception during valid array allocation: " + t);
        }
    }
}
