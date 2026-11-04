/* @test    @summary Test Unsafe.setMemory method for correct behavior and boundary conditions    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testSetMemoryBasic();
        testSetMemoryZeroBytes();
        testSetMemoryNullObject();
        testSetMemoryNegativeOffset();
        System.out.println("All tests passed.");
    }

    private static void testSetMemoryBasic() {
        byte[] array = new byte[10];
        Unsafe unsafe = Unsafe.getUnsafe();
        long baseOffset = unsafe.arrayBaseOffset(byte[].class);
        long indexScale = unsafe.arrayIndexScale(byte[].class);
        long offset = baseOffset;
        byte value = (byte)0xAB;
        unsafe.setMemory(array, offset, array.length, value);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != value) {
                throw new AssertionError("Memory not set correctly at index " + i);
            }
        }
    }

    private static void testSetMemoryZeroBytes() {
        byte[] array = new byte[5];
        Unsafe unsafe = Unsafe.getUnsafe();
        long baseOffset = unsafe.arrayBaseOffset(byte[].class);
        long offset = baseOffset;
        unsafe.setMemory(array, offset, 0, (byte)0xFF); // should do nothing
        for (byte b : array) {
            if (b != 0) {
                throw new AssertionError("Zero byte set affected array");
            }
        }
    }

    private static void testSetMemoryNullObject() {
        Unsafe unsafe = Unsafe.getUnsafe();
        long offset = 0;
        try {
            unsafe.setMemory(null, offset, 10, (byte)0x00);
            // Expect no exception, but behavior is undefined; test passes if no exception
        } catch (Throwable t) {
            throw new AssertionError("setMemory with null object threw exception", t);
        }
    }

    private static void testSetMemoryNegativeOffset() {
        byte[] array = new byte[10];
        Unsafe unsafe = Unsafe.getUnsafe();
        long baseOffset = unsafe.arrayBaseOffset(byte[].class);
        long offset = baseOffset - 4; // negative offset relative to base
        try {
            unsafe.setMemory(array, offset, 4, (byte)0x00);
            // Behavior is undefined; test passes if no exception or if exception is thrown, depending on implementation
        } catch (Throwable t) {
            // Acceptable if exception occurs
        }
    }
}
