/*
 * @test
 * @summary Deterministic tests for jdk.internal.misc.Unsafe.copyMemory(long,long,long)
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Basic sanity test: copy zero bytes between two arbitrary addresses
        // Expect no exception and no effect
        unsafe.copyMemory(0L, 0L, 0L);

        // Copy a small number of bytes between two addresses
        // Use addresses 1 and 2 (non-null, small values) to avoid null checks
        unsafe.copyMemory(1L, 2L, 1L);

        // Copy a larger number of bytes (e.g. 10) between two addresses
        unsafe.copyMemory(10L, 20L, 10L);

        // Copy zero bytes with same source and destination address
        unsafe.copyMemory(100L, 100L, 0L);

        // CopyMemory with negative bytes should not throw but behavior is undefined
        // According to current code, no exception is thrown, so test that no exception occurs
        unsafe.copyMemory(0L, 0L, -1L);

        // CopyMemory with large bytes value but valid addresses
        unsafe.copyMemory(0L, 0L, Long.MAX_VALUE >>> 1);

        // No exceptions expected for these calls as per current implementation
        System.out.println("All Unsafe.copyMemory tests passed.");
    }
}
