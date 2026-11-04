/* @test    @summary Test copyMemory method of Unsafe class for correct memory copying behavior    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testCopyMemory();
    }

    public static void testCopyMemory() {
        Unsafe unsafe = Unsafe.getUnsafe();
        byte[] source = new byte[10];
        byte[] destination = new byte[10];
        for (int i = 0; i < source.length; i++) {
            source[i] = (byte) i;
        }
        long baseOffset = unsafe.arrayBaseOffset(byte[].class);
        long indexScale = unsafe.arrayIndexScale(byte[].class);
        long srcAddress = unsafe.getLong(source, baseOffset);
        long destAddress = unsafe.getLong(destination, baseOffset);
        // Copy 10 bytes from source to destination
        unsafe.copyMemory(source, baseOffset, destination, baseOffset, source.length);
        boolean success = true;
        for (int i = 0; i < source.length; i++) {
            if (destination[i] != source[i]) {
                success = false;
                break;
            }
        }
        if (!success) {
            throw new AssertionError("Memory copy did not produce expected result");
        }
    }
}
