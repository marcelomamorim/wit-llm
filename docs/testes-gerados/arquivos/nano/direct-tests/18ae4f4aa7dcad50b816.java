/* @test    @summary Test allocateUninitializedArray method for primitive types    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testAllocateUninitializedArrayWithValidPrimitive();
        testAllocateUninitializedArrayWithNullComponentType();
        testAllocateUninitializedArrayWithNonPrimitiveType();
        testAllocateUninitializedArrayWithNegativeLength();
    }

    static void testAllocateUninitializedArrayWithValidPrimitive() {
        Unsafe unsafe = Unsafe.getUnsafe();
        Object array = unsafe.allocateUninitializedArray(int.class, 10);
        assert array != null : "Array should not be null";
        assert array.getClass().isArray() : "Result should be an array";
        assert array.getClass().getComponentType() == int.class : "Component type should be int";
        assert java.lang.reflect.Array.getLength(array) == 10 : "Array length should be 10";
    }

    static void testAllocateUninitializedArrayWithNullComponentType() {
        Unsafe unsafe = Unsafe.getUnsafe();
        boolean threw = false;
        try {
            unsafe.allocateUninitializedArray(null, 5);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        assert threw : "Should throw IllegalArgumentException for null componentType";
    }

    static void testAllocateUninitializedArrayWithNonPrimitiveType() {
        Unsafe unsafe = Unsafe.getUnsafe();
        boolean threw = false;
        try {
            unsafe.allocateUninitializedArray(String.class, 5);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        assert threw : "Should throw IllegalArgumentException for non-primitive componentType";
    }

    static void testAllocateUninitializedArrayWithNegativeLength() {
        Unsafe unsafe = Unsafe.getUnsafe();
        boolean threw = false;
        try {
            unsafe.allocateUninitializedArray(int.class, -1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        assert threw : "Should throw IllegalArgumentException for negative length";
    }
}
