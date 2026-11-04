/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.ensureClassInitialized method
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test that passing null throws NullPointerException
        try {
            unsafe.ensureClassInitialized(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test with a valid class (e.g., String.class) to ensure no exception
        try {
            unsafe.ensureClassInitialized(String.class);
        } catch (Throwable t) {
            throw new Error("Unexpected exception for valid class: " + t);
        }
    }
}
