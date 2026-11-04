/*
 * @test
 * @summary Checks observable Field.getLong behaviour backed by UnsafeLongFieldAccessorImpl.getLong
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    static class Holder {
        long value = 42L;
        String s = "x";
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("value");
        f.setAccessible(true);

        if (f.getLong(h) != 42L) {
            throw new RuntimeException("unexpected initial getLong value: " + f.getLong(h));
        }

        h.value = 123456789012L;
        if (f.getLong(h) != 123456789012L) {
            throw new RuntimeException("getLong did not observe updated value");
        }

        Object boxed = f.get(h);
        if (!(boxed instanceof Long) || ((Long) boxed).longValue() != 123456789012L) {
            throw new RuntimeException("get() inconsistent with getLong(): " + boxed);
        }

        try {
            f.getLong("not a Holder");
            throw new RuntimeException("expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected path: object not assignable to declaring class
        }

        try {
            f.getLong(new Object());
            throw new RuntimeException("expected IllegalArgumentException for unrelated object");
        } catch (IllegalArgumentException expected) {
            // expected path: object not assignable to declaring class
        }
    }
}
