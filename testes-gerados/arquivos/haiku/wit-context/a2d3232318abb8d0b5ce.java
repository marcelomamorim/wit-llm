/*
 * @test
 * @summary Witup-generated checks for Unsafe.arrayBaseOffset null handling
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        boolean thrown = false;
        try {
            unsafe.arrayBaseOffset(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null arrayClass");
        }

        int intOffset = unsafe.arrayBaseOffset(int[].class);
        if (intOffset < 0) {
            throw new RuntimeException("unexpected negative base offset for int[]: " + intOffset);
        }

        int objOffset = unsafe.arrayBaseOffset(Object[].class);
        if (objOffset < 0) {
            throw new RuntimeException("unexpected negative base offset for Object[]: " + objOffset);
        }
    }
}
