/*
 * @test
 * @summary Zero-length Unsafe.setMemory calls must complete without side effects
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        if (unsafe == null) {
            throw new RuntimeException("getUnsafe returned null");
        }
        if (Unsafe.getUnsafe() != unsafe) {
            throw new RuntimeException("getUnsafe must return the same instance");
        }

        unsafe.setMemory(null, 0L, 0L, (byte) 0);
        unsafe.setMemory(null, 0L, 0L, (byte) 0x7F);
        unsafe.setMemory(new byte[1], 0L, 0L, (byte) 1);
    }
}
