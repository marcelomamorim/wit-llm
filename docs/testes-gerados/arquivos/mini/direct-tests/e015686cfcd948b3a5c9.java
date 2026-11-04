/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.arrayIndexScale method behavior
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test with a valid array class: int[].class
        int scaleIntArray = unsafe.arrayIndexScale(int[].class);
        if (scaleIntArray <= 0) {
            throw new RuntimeException("arrayIndexScale for int[] should be positive, got: " + scaleIntArray);
        }

        // Test with a valid array class: Object[].class
        int scaleObjectArray = unsafe.arrayIndexScale(Object[].class);
        if (scaleObjectArray <= 0) {
            throw new RuntimeException("arrayIndexScale for Object[] should be positive, got: " + scaleObjectArray);
        }

        // Test with a non-array class: String.class
        // The behavior is not specified here, but it should not throw NullPointerException
        // We just call and accept the returned int (may be 0 or some value)
        int scaleStringClass = unsafe.arrayIndexScale(String.class);

        // Test with null argument: must throw NullPointerException
        try {
            unsafe.arrayIndexScale(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException npe) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
