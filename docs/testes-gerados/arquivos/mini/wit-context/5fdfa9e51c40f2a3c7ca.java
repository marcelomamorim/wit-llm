/*
 * @test
 * @summary Test Unsafe.objectFieldOffset(Class<?>, String) for null argument checks and normal behavior
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test null class argument triggers NullPointerException
        try {
            unsafe.objectFieldOffset(null, "anyField");
            throw new Error("Expected NullPointerException for null class argument");
        } catch (NullPointerException expected) {
        }

        // Test null name argument triggers NullPointerException
        try {
            unsafe.objectFieldOffset(Object.class, null);
            throw new Error("Expected NullPointerException for null name argument");
        } catch (NullPointerException expected) {
        }

        // Test with valid arguments returns a non-negative offset
        // Since we cannot create arbitrary fields, use a known field from Object class
        // Object class has a private field 'hashCode' or 'shadow$_klass_' is internal, so test with a public field in a test class
        class TestClass {
            public int testField;
        }

        long offset = unsafe.objectFieldOffset(TestClass.class, "testField");
        if (offset < 0) {
            throw new Error("objectFieldOffset returned negative offset");
        }

        // Test with non-existent field name throws RuntimeException (NoSuchFieldException wrapped)
        // The actual exception type is not declared, so catch RuntimeException
        try {
            unsafe.objectFieldOffset(TestClass.class, "nonExistentField");
            throw new Error("Expected RuntimeException for non-existent field name");
        } catch (RuntimeException expected) {
        }
    }
}
