/* @test    @summary Test Unsafe.arrayIndexScale method for various array classes    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testArrayIndexScaleWithIntArray();
        testArrayIndexScaleWithObjectArray();
        testArrayIndexScaleWithPrimitiveWrapper();
        testArrayIndexScaleWithNull();
    }

    static void testArrayIndexScaleWithIntArray() {
        Unsafe unsafe = Unsafe.getUnsafe();
        int scale = unsafe.arrayIndexScale(int[].class);
        // For int[], the scale should be 4 (size of int)
        if (scale != 4) {
            throw new AssertionError("Expected scale 4 for int[], but got " + scale);
        }
    }

    static void testArrayIndexScaleWithObjectArray() {
        Unsafe unsafe = Unsafe.getUnsafe();
        int scale = unsafe.arrayIndexScale(Object[].class);
        // For Object[], the scale should be size of reference (typically 4 or 8)
        if (scale != 4 && scale != 8) {
            throw new AssertionError("Expected scale 4 or 8 for Object[], but got " + scale);
        }
    }

    static void testArrayIndexScaleWithPrimitiveWrapper() {
        Unsafe unsafe = Unsafe.getUnsafe();
        int scale = unsafe.arrayIndexScale(Integer[].class);
        // For Integer[], the scale should be same as Object[]
        if (scale != 4 && scale != 8) {
            throw new AssertionError("Expected scale 4 or 8 for Integer[], but got " + scale);
        }
    }

    static void testArrayIndexScaleWithNull() {
        Unsafe unsafe = Unsafe.getUnsafe();
        boolean exceptionThrown = false;
        try {
            unsafe.arrayIndexScale(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null");
        }
    }
}
