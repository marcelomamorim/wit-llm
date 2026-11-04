/*
 * @test
 * @summary Verifies Field.getInt on a volatile short field, which is served by UnsafeQualifiedShortFieldAccessorImpl.getInt
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class Holder {
        volatile short s = 7;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("s");
        Holder h = new Holder();

        if (f.getInt(h) != 7) {
            throw new AssertionError("expected 7 but got " + f.getInt(h));
        }

        h.s = -5;
        if (f.getInt(h) != -5) {
            throw new AssertionError("expected -5 after update");
        }

        h.s = Short.MIN_VALUE;
        if (f.getInt(h) != Short.MIN_VALUE) {
            throw new AssertionError("expected Short.MIN_VALUE");
        }

        h.s = Short.MAX_VALUE;
        if (f.getInt(h) != Short.MAX_VALUE) {
            throw new AssertionError("expected Short.MAX_VALUE");
        }

        boolean threw = false;
        try {
            f.getInt(new Object());
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("expected IllegalArgumentException for wrong object type");
        }
    }
}
