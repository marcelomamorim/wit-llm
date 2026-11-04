/*
 * @test
 * @summary Test Unsafe.getAndAddCharAcquire behavior
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test normal behavior with a valid offset and delta
        // Since we cannot create arbitrary objects with known offsets,
        // test with a simple array and known offset for char element
        char[] arr = new char[] {10, 20, 30};
        long baseOffset = unsafe.arrayBaseOffset(char[].class);
        long indexScale = unsafe.arrayIndexScale(char[].class);

        // Use offset for element 1
        long offset = baseOffset + indexScale;

        char original = unsafe.getCharVolatile(arr, offset);
        char delta = 5;
        char result = unsafe.getAndAddCharAcquire(arr, offset, delta);

        // The returned value should be the original value
        if (result != original) {
            throw new Error("Returned value " + result + " != original " + original);
        }

        // The new value at offset should be original + delta
        char newValue = unsafe.getCharVolatile(arr, offset);
        if (newValue != (char)(original + delta)) {
            throw new Error("New value " + newValue + " != original + delta " + (original + delta));
        }

        // Test that method returns a char and does not throw for valid inputs
        // No direct test for IllegalArgumentException because it depends on internal offset alignment and endianness
        // which cannot be reliably triggered here without unsafe internal state manipulation

        System.out.println("Unsafe.getAndAddCharAcquire test passed");
    }
}
