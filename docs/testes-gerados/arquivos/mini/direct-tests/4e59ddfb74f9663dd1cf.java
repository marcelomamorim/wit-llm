/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.copyMemory(long,long,long) method
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Allocate two byte arrays
        byte[] src = new byte[] {1, 2, 3, 4, 5};
        byte[] dest = new byte[] {0, 0, 0, 0, 0};

        // Get base offset of byte[]
        long baseOffset = unsafe.arrayBaseOffset(byte[].class);

        // Copy 5 bytes from src to dest
        unsafe.copyMemory(src, baseOffset, dest, baseOffset, src.length);

        // Verify that dest now contains the same bytes as src
        for (int i = 0; i < src.length; i++) {
            if (dest[i] != src[i]) {
                throw new RuntimeException("copyMemory failed at index " + i + ": expected " + src[i] + ", got " + dest[i]);
            }
        }

        // Test copyMemory with zero bytes (should do nothing and not fail)
        unsafe.copyMemory(src, baseOffset, dest, baseOffset, 0);

        // Test copyMemory with overlapping regions in the same array
        // Copy first 3 bytes to last 3 bytes (overlapping)
        unsafe.copyMemory(src, baseOffset, src, baseOffset + 2, 3);
        // Expected src now: {1, 2, 1, 2, 3}
        if (src[2] != 1 || src[3] != 2 || src[4] != 3) {
            throw new RuntimeException("copyMemory overlapping copy failed: " +
                src[2] + ", " + src[3] + ", " + src[4]);
        }

        // Test copyMemory with negative bytes (should not throw, but no effect expected)
        // As per source, no explicit check, so behavior undefined; just call to check no crash
        unsafe.copyMemory(src, baseOffset, dest, baseOffset, -1);

        System.out.println("All tests passed.");
    }
}
