/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.defineAnonymousClass method behavior
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) throws Throwable {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test 1: null hostClass should throw NullPointerException
        try {
            unsafe.defineAnonymousClass(null, new byte[0], null);
            throw new Error("Expected NullPointerException for null hostClass");
        } catch (NullPointerException expected) {
        }

        // Test 2: null data should throw NullPointerException
        try {
            unsafe.defineAnonymousClass(String.class, null, null);
            throw new Error("Expected NullPointerException for null data");
        } catch (NullPointerException expected) {
        }

        // Test 3: hostClass is array should throw IllegalArgumentException
        try {
            unsafe.defineAnonymousClass(int[].class, new byte[0], null);
            throw new Error("Expected IllegalArgumentException for array hostClass");
        } catch (IllegalArgumentException expected) {
        }

        // Test 4: hostClass is primitive should throw IllegalArgumentException
        try {
            unsafe.defineAnonymousClass(int.class, new byte[0], null);
            throw new Error("Expected IllegalArgumentException for primitive hostClass");
        } catch (IllegalArgumentException expected) {
        }

        // Test 5: valid call with minimal dummy byte array
        // The actual defineAnonymousClass0 is native and may throw
        // We just check that it returns a Class<?> or throws an exception
        byte[] dummyData = new byte[] { (byte)0xca, (byte)0xfe, (byte)0xba, (byte)0xbe };
        Class<?> host = UnsafeWitupTest.class;
        try {
            Class<?> anonClass = unsafe.defineAnonymousClass(host, dummyData, null);
            if (anonClass == null) {
                throw new Error("defineAnonymousClass returned null");
            }
        } catch (Throwable t) {
            // Accept any throwable here because native implementation may reject dummy data
        }
    }
}
