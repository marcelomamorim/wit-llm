/*
 * @test
 * @summary Checks argument validation and primitive array allocation of Unsafe.allocateUninitializedArray
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        Object intArray = unsafe.allocateUninitializedArray(int.class, 10);
        if (!(intArray instanceof int[]) || ((int[]) intArray).length != 10) {
            throw new RuntimeException("int[10] expected");
        }

        Object byteArray = unsafe.allocateUninitializedArray(byte.class, 3);
        if (!(byteArray instanceof byte[]) || ((byte[]) byteArray).length != 3) {
            throw new RuntimeException("byte[3] expected");
        }

        Object longArray = unsafe.allocateUninitializedArray(long.class, 0);
        if (!(longArray instanceof long[]) || ((long[]) longArray).length != 0) {
            throw new RuntimeException("long[0] expected");
        }

        expectIAE(() -> unsafe.allocateUninitializedArray(null, 5), "null component type");
        expectIAE(() -> unsafe.allocateUninitializedArray(Object.class, 5), "non-primitive Object");
        expectIAE(() -> unsafe.allocateUninitializedArray(String.class, 5), "non-primitive String");
        expectIAE(() -> unsafe.allocateUninitializedArray(int.class, -1), "negative length");
    }

    private static void expectIAE(Runnable r, String msg) {
        try {
            r.run();
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected: " + msg);
    }
}
