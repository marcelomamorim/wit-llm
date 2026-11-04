/*
 * @test
 * @summary Verify Unsafe.copyMemory(long,long,long) copies raw bytes between native addresses and handles zero-length copies
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        final int n = 8;
        long src = unsafe.allocateMemory(n);
        long dst = unsafe.allocateMemory(n);
        try {
            for (int i = 0; i < n; i++) {
                unsafe.putByte(src + i, (byte) (i + 1));
                unsafe.putByte(dst + i, (byte) 0);
            }

            unsafe.copyMemory(src, dst, n);

            for (int i = 0; i < n; i++) {
                check(unsafe.getByte(dst + i) == (byte) (i + 1),
                      "byte " + i + " was not copied");
            }

            unsafe.putByte(dst, (byte) 42);
            unsafe.copyMemory(src, dst, 0);
            check(unsafe.getByte(dst) == (byte) 42,
                  "zero-length copy modified destination");
        } finally {
            unsafe.freeMemory(src);
            unsafe.freeMemory(dst);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
