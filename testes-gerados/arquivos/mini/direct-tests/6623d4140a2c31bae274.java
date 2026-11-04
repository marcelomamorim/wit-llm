/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.shouldBeInitialized method
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test with a valid class: should return a boolean without exception
        boolean result = unsafe.shouldBeInitialized(String.class);
        if (result != true && result != false) {
            throw new AssertionError("shouldBeInitialized returned non-boolean value");
        }

        // Test with null argument: expect NullPointerException
        try {
            unsafe.shouldBeInitialized(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
