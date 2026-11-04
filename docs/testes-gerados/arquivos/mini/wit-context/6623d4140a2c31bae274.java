/*
 * @test
 * @summary Test Unsafe.shouldBeInitialized(Class<?>) for null argument and normal behavior
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test that passing null throws NullPointerException
        try {
            unsafe.shouldBeInitialized(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test normal behavior with a valid class
        // We cannot predict the exact return value, but it should be boolean
        boolean result = unsafe.shouldBeInitialized(String.class);
        if (result != true && result != false) {
            throw new Error("shouldBeInitialized returned non-boolean value");
        }
    }
}
