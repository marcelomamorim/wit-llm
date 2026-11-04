/* @test    @summary Test Unsafe.allocateMemory for various inputs    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testAllocateMemoryZero();
        testAllocateMemoryPositive();
        testAllocateMemoryLarge();
    }

    static void testAllocateMemoryZero() {
        Unsafe unsafe = Unsafe.getUnsafe();
        long address = unsafe.allocateMemory(0L);
        assert address == 0L : "Expected address 0 for zero bytes";
    }

    static void testAllocateMemoryPositive() {
        Unsafe unsafe = Unsafe.getUnsafe();
        long size = 1024L;
        long address = unsafe.allocateMemory(size);
        assert address != 0L : "Address should not be zero for positive size";
        // Note: Memory is not freed here; in real tests, freeMemory should be called.
    }

    static void testAllocateMemoryLarge() {
        Unsafe unsafe = Unsafe.getUnsafe();
        long size = 1L << 30; // 1GB
        long address = unsafe.allocateMemory(size);
        assert address != 0L : "Address should not be zero for large size";
        // Note: Memory is not freed here; in real tests, freeMemory should be called.
    }
}
