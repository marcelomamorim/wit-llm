/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.allocateMemory(long) method behavior
 * @run main UnsafeWitupTest
 */

public class UnsafeWitupTest {
    public static void main(String[] args) throws Exception {
        jdk.internal.misc.Unsafe unsafe = jdk.internal.misc.Unsafe.getUnsafe();

        // Test allocateMemory with zero bytes returns 0
        long zeroResult = unsafe.allocateMemory(0L);
        if (zeroResult != 0L) {
            throw new Error("allocateMemory(0) should return 0 but returned " + zeroResult);
        }

        // Test allocateMemory with positive bytes returns non-zero address
        long size = 16L;
        long address = unsafe.allocateMemory(size);
        if (address == 0L) {
            throw new Error("allocateMemory(" + size + ") returned 0, expected non-zero address");
        }

        // Free allocated memory to avoid leaks (if freeMemory is available)
        // Defensive: check if freeMemory method exists
        try {
            java.lang.reflect.Method freeMemoryMethod = jdk.internal.misc.Unsafe.class.getMethod("freeMemory", long.class);
            freeMemoryMethod.invoke(unsafe, address);
        } catch (NoSuchMethodException e) {
            // freeMemory not available, ignore
        }

        // Test allocateMemory with negative bytes triggers allocateMemoryChecks exception
        // The source code snippet does not show allocateMemoryChecks implementation,
        // so we cannot assert exact exception type or message.
        // We test that an exception is thrown for negative input.
        boolean negativeThrew = false;
        try {
            unsafe.allocateMemory(-1L);
        } catch (Throwable t) {
            negativeThrew = true;
        }
        if (!negativeThrew) {
            throw new Error("allocateMemory(-1) should throw an exception but did not");
        }
    }
}
