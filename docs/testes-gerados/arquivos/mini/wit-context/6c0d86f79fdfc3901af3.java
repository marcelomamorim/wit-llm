/*
 * @test
 * @summary Test Unsafe.objectFieldOffset(Field) for null argument and normal usage
 * @run main UnsafeWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test that passing null throws NullPointerException
        try {
            unsafe.objectFieldOffset(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test normal usage: get offset of a known field
        Field field = Sample.class.getDeclaredField("value");
        long offset = unsafe.objectFieldOffset(field);
        if (offset < 0) {
            throw new Error("objectFieldOffset returned negative offset");
        }

        // Additional sanity: calling twice returns same offset
        long offset2 = unsafe.objectFieldOffset(field);
        if (offset != offset2) {
            throw new Error("objectFieldOffset returned inconsistent offsets");
        }

        System.out.println("UnsafeWitupTest passed");
    }

    static class Sample {
        private int value = 42;
    }
}
