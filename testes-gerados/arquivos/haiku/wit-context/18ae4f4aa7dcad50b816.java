/*
 * @test
 * @summary Witup-generated checks for Unsafe.allocateUninitializedArray argument validation and observable allocation
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        if (unsafe == null) {
            throw new RuntimeException("Unsafe.getUnsafe() returned null");
        }

        expectIllegalArgument(unsafe, null, 4, "null componentType");
        expectIllegalArgument(unsafe, String.class, 4, "non-primitive componentType");
        expectIllegalArgument(unsafe, int.class, -1, "negative length");

        Object ints = unsafe.allocateUninitializedArray(int.class, 3);
        if (ints.getClass() != int[].class) {
            throw new RuntimeException("Expected int[] but got " + ints.getClass());
        }
        if (((int[]) ints).length != 3) {
            throw new RuntimeException("Unexpected int[] length: " + ((int[]) ints).length);
        }

        Object longs = unsafe.allocateUninitializedArray(long.class, 0);
        if (longs.getClass() != long[].class) {
            throw new RuntimeException("Expected long[] but got " + longs.getClass());
        }
        if (((long[]) longs).length != 0) {
            throw new RuntimeException("Unexpected long[] length: " + ((long[]) longs).length);
        }
    }

    private static void expectIllegalArgument(Unsafe unsafe, Class<?> componentType, int length, String label) {
        try {
            unsafe.allocateUninitializedArray(componentType, length);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for " + label);
    }
}
