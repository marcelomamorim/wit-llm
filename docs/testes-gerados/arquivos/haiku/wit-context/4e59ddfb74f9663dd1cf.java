/*
 * @test
 * @summary Witup-derived checks for Unsafe.copyMemory(long, long, long): zero-length copy and negative length rejection
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        unsafe.copyMemory(0L, 0L, 0L);

        boolean thrown = false;
        try {
            unsafe.copyMemory(0L, 0L, -1L);
        } catch (RuntimeException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("copyMemory accepted negative byte count");
        }
    }
}
