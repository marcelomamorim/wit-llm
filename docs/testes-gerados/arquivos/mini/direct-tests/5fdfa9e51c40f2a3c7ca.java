/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.objectFieldOffset method
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test normal case: get offset of a known field
        long offset = unsafe.objectFieldOffset(TestClass.class, "value");
        if (offset < 0) {
            throw new RuntimeException("Unexpected negative offset");
        }

        // Test null class argument throws NullPointerException
        try {
            unsafe.objectFieldOffset(null, "value");
            throw new RuntimeException("Expected NullPointerException for null class");
        } catch (NullPointerException expected) {
        }

        // Test null name argument throws NullPointerException
        try {
            unsafe.objectFieldOffset(TestClass.class, null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
        }

        // Test non-existent field name throws RuntimeException (reflective failure)
        try {
            unsafe.objectFieldOffset(TestClass.class, "nonExistentField");
            throw new RuntimeException("Expected RuntimeException for non-existent field");
        } catch (RuntimeException expected) {
        }

        System.out.println("PASS");
    }

    static class TestClass {
        int value;
    }
}
