/* @test    @summary Test allocateMemory for various edge cases    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testAllocateMemoryZeroBytes();
        testAllocateMemoryNegativeBytes();
        testAllocateMemoryNormal();
    }

    private static void testAllocateMemoryZeroBytes() {
        Unsafe unsafe = Unsafe.getUnsafe();
        long result = unsafe.allocateMemory(0L);
        if (result != 0L) {
            throw new AssertionError("Expected allocateMemory(0) to return 0, but got " + result);
        }
    }

    private static void testAllocateMemoryNegativeBytes() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            unsafe.allocateMemory(-1L);
            throw new AssertionError("Expected allocateMemory(-1) to throw OutOfMemoryError");
        } catch (OutOfMemoryError e) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for allocateMemory(-1): " + t);
        }
    }

    private static void testAllocateMemoryNormal() {
        Unsafe unsafe = Unsafe.getUnsafe();
        long size = 1024L;
        long address = unsafe.allocateMemory(size);
        if (address == 0L) {
            throw new AssertionError("allocateMemory(1024) returned 0, expected non-zero address");
        }
        // Optionally, free memory if method available (not shown in source excerpt)
    }
}
