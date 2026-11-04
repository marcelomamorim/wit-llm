/*
 * @test
 * @summary Witup-generated tests for Unsafe.staticFieldOffset(Field)
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    static int first;
    static int second;

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        try {
            unsafe.staticFieldOffset(null);
            throw new AssertionError("expected NullPointerException for null Field");
        } catch (NullPointerException e) {
            // expected
        }

        Field f1 = UnsafeWitupTest.class.getDeclaredField("first");
        Field f2 = UnsafeWitupTest.class.getDeclaredField("second");

        long o1 = unsafe.staticFieldOffset(f1);
        long o1again = unsafe.staticFieldOffset(f1);
        if (o1 != o1again) {
            throw new AssertionError("offset not stable: " + o1 + " vs " + o1again);
        }

        long o2 = unsafe.staticFieldOffset(f2);
        if (o1 == o2) {
            throw new AssertionError("distinct static fields share offset: " + o1);
        }
    }
}
