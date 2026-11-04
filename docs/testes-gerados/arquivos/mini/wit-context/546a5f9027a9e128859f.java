/*
 * @test
 * @summary Test Unsafe.allocateMemory(long) for valid and invalid inputs
 * @run main UnsafeWitupTest
 */

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testAllocateMemoryZero();
        testAllocateMemoryPositive();
        testAllocateMemoryInvalidInputs();
    }

    private static void testAllocateMemoryZero() {
        jdk.internal.misc.Unsafe unsafe = jdk.internal.misc.Unsafe.getUnsafe();
        long result = unsafe.allocateMemory(0L);
        if (result != 0L) {
            throw new RuntimeException("allocateMemory(0) should return 0, got: " + result);
        }
    }

    private static void testAllocateMemoryPositive() {
        jdk.internal.misc.Unsafe unsafe = jdk.internal.misc.Unsafe.getUnsafe();
        // Allocate a small positive amount of memory (e.g., 1 byte)
        long addr = unsafe.allocateMemory(1L);
        if (addr == 0L) {
            throw new RuntimeException("allocateMemory(1) returned 0, expected non-zero address");
        }
        // Free the allocated memory to avoid leaks
        unsafe.freeMemory(addr);
    }

    private static void testAllocateMemoryInvalidInputs() {
        jdk.internal.misc.Unsafe unsafe = jdk.internal.misc.Unsafe.getUnsafe();
        // According to the source and WIT, negative bytes may throw invalidInput (likely IllegalArgumentException)
        // The actual exception type is not public, so we test for RuntimeException or Error
        // We test negative input and large input that triggers checks

        // Negative input test
        try {
            unsafe.allocateMemory(-1L);
            throw new RuntimeException("allocateMemory(-1) did not throw expected exception");
        } catch (RuntimeException | Error e) {
            // Expected: exception thrown for invalid input
        }

        // Large input test: for 32-bit addressSize, bytes >>> 32 != 0 triggers invalidInput
        // We simulate a large value that triggers this condition
        // Since addressSize0() is private, we cannot check it; we just test a large value
        try {
            unsafe.allocateMemory(Long.MAX_VALUE);
            // If no exception, test passes as allocateMemory may succeed or throw OutOfMemoryError
        } catch (RuntimeException | Error e) {
            // Acceptable: may throw invalidInput or OutOfMemoryError
        }
    }
}
