/*
 * @test
 * @summary Test for Unsafe.getAndAddCharAcquire method exception paths
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testGetAndAddCharAcquireThrowsIllegalArgumentExceptionWhenOffsetMisalignedBigEndian();
        testGetAndAddCharAcquireThrowsIllegalArgumentExceptionWhenOffsetMisalignedLittleEndian();
    }

    private static void testGetAndAddCharAcquireThrowsIllegalArgumentExceptionWhenOffsetMisalignedBigEndian() {
        Unsafe unsafe = Unsafe.getUnsafe();
        Object obj = new Object();
        long offset = 1L; // misaligned offset (not multiple of 4)
        char delta = 'A';
        boolean threw = false;
        try {
            unsafe.getAndAddCharAcquire(obj, offset, delta);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for misaligned offset on big-endian");
        }
    }

    private static void testGetAndAddCharAcquireThrowsIllegalArgumentExceptionWhenOffsetMisalignedLittleEndian() {
        Unsafe unsafe = Unsafe.getUnsafe();
        Object obj = new Object();
        long offset = 2L; // misaligned offset (not multiple of 4)
        char delta = 'A';
        boolean threw = false;
        try {
            unsafe.getAndAddCharAcquire(obj, offset, delta);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for misaligned offset on little-endian");
        }
    }
}
