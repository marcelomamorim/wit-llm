/*
 * @test
 * @summary Test Unsafe.arrayBaseOffset behavior including null argument exception
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test that passing null throws NullPointerException
        try {
            unsafe.arrayBaseOffset(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test with a valid array class: int[].class
        int offset = unsafe.arrayBaseOffset(int[].class);
        if (offset < 0) {
            throw new Error("arrayBaseOffset returned negative offset: " + offset);
        }

        // Test with a valid array class: Object[].class
        offset = unsafe.arrayBaseOffset(Object[].class);
        if (offset < 0) {
            throw new Error("arrayBaseOffset returned negative offset: " + offset);
        }

        // Test with a non-array class: String.class
        // The behavior is not specified here, but should not throw NullPointerException
        // We just call and check it does not throw
        try {
            int off = unsafe.arrayBaseOffset(String.class);
            if (off < 0) {
                throw new Error("arrayBaseOffset returned negative offset for non-array class: " + off);
            }
        } catch (NullPointerException e) {
            throw new Error("Unexpected NullPointerException for non-null argument", e);
        }

        System.out.println("Unsafe.arrayBaseOffset tests passed");
    }
}
