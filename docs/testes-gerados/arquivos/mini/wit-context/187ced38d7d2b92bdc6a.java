/*
 * @test
 * @summary Test Unsafe.defineAnonymousClass for argument validation and normal behavior
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test NPE when hostClass is null
        try {
            unsafe.defineAnonymousClass(null, new byte[1], null);
            throw new Error("Expected NullPointerException for null hostClass");
        } catch (NullPointerException expected) {
        }

        // Test NPE when data is null
        try {
            unsafe.defineAnonymousClass(String.class, null, null);
            throw new Error("Expected NullPointerException for null data");
        } catch (NullPointerException expected) {
        }

        // Test IllegalArgumentException when hostClass is array
        try {
            unsafe.defineAnonymousClass(int[].class, new byte[1], null);
            throw new Error("Expected IllegalArgumentException for array hostClass");
        } catch (IllegalArgumentException expected) {
        }

        // Test IllegalArgumentException when hostClass is primitive
        try {
            unsafe.defineAnonymousClass(int.class, new byte[1], null);
            throw new Error("Expected IllegalArgumentException for primitive hostClass");
        } catch (IllegalArgumentException expected) {
        }

        // Test normal behavior: defineAnonymousClass0 is native, so just test no exception for valid input
        // Use Object.class as hostClass and empty byte array
        byte[] dummyData = new byte[] {0};
        Object[] dummyPatches = null;
        Class<?> result = unsafe.defineAnonymousClass(Object.class, dummyData, dummyPatches);
        if (result == null) {
            throw new Error("defineAnonymousClass returned null unexpectedly");
        }
    }
}
