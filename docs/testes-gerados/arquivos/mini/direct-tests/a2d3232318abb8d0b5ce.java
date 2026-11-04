/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.arrayBaseOffset method
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test with a valid array class: int[].class
        int offset = unsafe.arrayBaseOffset(int[].class);
        if (offset < 0) {
            throw new RuntimeException("arrayBaseOffset returned negative offset: " + offset);
        }

        // Test with a valid array class: Object[].class
        offset = unsafe.arrayBaseOffset(Object[].class);
        if (offset < 0) {
            throw new RuntimeException("arrayBaseOffset returned negative offset: " + offset);
        }

        // Test with a non-array class: String.class
        // The method does not explicitly forbid this, so just check it returns an int (>=0)
        offset = unsafe.arrayBaseOffset(String.class);
        if (offset < 0) {
            throw new RuntimeException("arrayBaseOffset returned negative offset for non-array class: " + offset);
        }

        // Test with null argument: expect NullPointerException
        try {
            unsafe.arrayBaseOffset(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
