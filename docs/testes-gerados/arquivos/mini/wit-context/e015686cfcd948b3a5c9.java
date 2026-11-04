/*
 * @test
 * @summary Test Unsafe.arrayIndexScale behavior including NPE on null input
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test that passing null throws NullPointerException
        try {
            unsafe.arrayIndexScale(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test with a valid array class returns positive scale
        int scale = unsafe.arrayIndexScale(byte[].class);
        if (scale <= 0) {
            throw new Error("arrayIndexScale returned non-positive scale: " + scale);
        }

        // Test with a non-array class returns 0 or throws (behavior depends on implementation)
        // We test that it does not throw NPE or other unexpected exceptions
        try {
            int nonArrayScale = unsafe.arrayIndexScale(String.class);
            if (nonArrayScale < 0) {
                throw new Error("arrayIndexScale returned negative scale for non-array class: " + nonArrayScale);
            }
        } catch (NullPointerException npe) {
            throw new Error("Unexpected NullPointerException for non-null input");
        } catch (Throwable t) {
            // Accept other exceptions as implementation detail
        }
    }
}
