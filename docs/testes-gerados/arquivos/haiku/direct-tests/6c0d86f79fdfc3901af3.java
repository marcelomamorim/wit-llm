/*
 * @test
 * @summary Verifies Unsafe.objectFieldOffset(Field) rejects null and returns stable, distinct offsets for distinct fields
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;
import java.lang.reflect.Field;

public class UnsafeWitupTest {

    static class Holder {
        int a;
        int b;
    }

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        Field fa = Holder.class.getDeclaredField("a");
        Field fb = Holder.class.getDeclaredField("b");

        boolean npeThrown = false;
        try {
            unsafe.objectFieldOffset(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("objectFieldOffset(null) did not throw NullPointerException");
        }

        long oa1 = unsafe.objectFieldOffset(fa);
        long oa2 = unsafe.objectFieldOffset(fa);
        if (oa1 != oa2) {
            throw new RuntimeException("offset for same field not stable: " + oa1 + " vs " + oa2);
        }

        long ob = unsafe.objectFieldOffset(fb);
        if (oa1 == ob) {
            throw new RuntimeException("distinct fields returned same offset: " + oa1);
        }
    }
}
