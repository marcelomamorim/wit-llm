/*
 * @test
 * @summary Witup-derived tests for Unsafe.allocateMemory observable behavior
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        testZeroBytesReturnsZero(unsafe);
        testNegativeBytesThrowsIllegalArgument(unsafe);
        testAllocateWriteReadFree(unsafe);
    }

    private static void testZeroBytesReturnsZero(Unsafe unsafe) {
        long p = unsafe.allocateMemory(0);
        if (p != 0) {
            throw new RuntimeException("allocateMemory(0) expected 0 but was " + p);
        }
    }

    private static void testNegativeBytesThrowsIllegalArgument(Unsafe unsafe) {
        try {
            unsafe.allocateMemory(-1L);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("allocateMemory(-1) did not throw IllegalArgumentException");
    }

    private static void testAllocateWriteReadFree(Unsafe unsafe) {
        long p = unsafe.allocateMemory(8);
        try {
            if (p == 0) {
                throw new RuntimeException("allocateMemory(8) returned 0");
            }
            unsafe.putByte(p, (byte) 0x5A);
            byte b = unsafe.getByte(p);
            if (b != (byte) 0x5A) {
                throw new RuntimeException("Unexpected byte read back: " + b);
            }
        } finally {
            if (p != 0) {
                unsafe.freeMemory(p);
            }
        }
    }
}
