/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.ensureClassInitialized method
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test normal behavior with a valid class
        unsafe.ensureClassInitialized(String.class);

        // Test that passing null throws NullPointerException
        try {
            unsafe.ensureClassInitialized(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
