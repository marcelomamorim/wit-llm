/*
 * @test
 * @summary Behavioral checks for Unsafe.getAndAddShortAcquire on an instance short field
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;
import java.lang.reflect.Field;

public class UnsafeWitupTest {

    static class Holder {
        short f;
    }

    public static void main(String[] args) throws Exception {
        Unsafe u = Unsafe.getUnsafe();
        Holder h = new Holder();
        Field field = Holder.class.getDeclaredField("f");
        long off = u.objectFieldOffset(field);

        h.f = 5;
        short r = u.getAndAddShortAcquire(h, off, (short) 3);
        if (r != 5) {
            throw new AssertionError("expected previous value 5 but was " + r);
        }
        if (h.f != 8) {
            throw new AssertionError("expected field 8 but was " + h.f);
        }

        h.f = Short.MAX_VALUE;
        r = u.getAndAddShortAcquire(h, off, (short) 1);
        if (r != Short.MAX_VALUE) {
            throw new AssertionError("expected previous value " + Short.MAX_VALUE + " but was " + r);
        }
        if (h.f != Short.MIN_VALUE) {
            throw new AssertionError("expected wraparound to " + Short.MIN_VALUE + " but was " + h.f);
        }

        h.f = 10;
        r = u.getAndAddShortAcquire(h, off, (short) -4);
        if (r != 10) {
            throw new AssertionError("expected previous value 10 but was " + r);
        }
        if (h.f != 6) {
            throw new AssertionError("expected field 6 but was " + h.f);
        }
    }
}
