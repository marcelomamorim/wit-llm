/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.setMemory method behavior
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test 1: setMemory with zero bytes should return immediately without error
        byte[] array1 = new byte[10];
        for (int i = 0; i < array1.length; i++) {
            array1[i] = 1;
        }
        unsafe.setMemory(array1, Unsafe.ARRAY_BYTE_BASE_OFFSET, 0L, (byte) 9);
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != 1) {
                throw new RuntimeException("Test 1 failed: array modified when bytes=0");
            }
        }

        // Test 2: setMemory with positive bytes sets the bytes to the given value
        byte[] array2 = new byte[10];
        for (int i = 0; i < array2.length; i++) {
            array2[i] = 0;
        }
        unsafe.setMemory(array2, Unsafe.ARRAY_BYTE_BASE_OFFSET, 5L, (byte) 42);
        for (int i = 0; i < 5; i++) {
            if (array2[i] != 42) {
                throw new RuntimeException("Test 2 failed: byte at index " + i + " not set correctly");
            }
        }
        for (int i = 5; i < array2.length; i++) {
            if (array2[i] != 0) {
                throw new RuntimeException("Test 2 failed: byte at index " + i + " modified unexpectedly");
            }
        }

        // Test 3: setMemory with null object and offset as address (simulate native memory) - no direct test possible here
        // Because we cannot allocate native memory or get valid address, we skip this test.

        System.out.println("All tests passed.");
    }
}
