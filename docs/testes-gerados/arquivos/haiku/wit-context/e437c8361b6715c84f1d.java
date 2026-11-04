/*
 * @test
 * @summary Verify Field.getDouble on a volatile short field, which uses UnsafeQualifiedShortFieldAccessorImpl, returns the widened value and rejects objects of an unrelated type with IllegalArgumentException
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class Holder {
        volatile short v;
    }

    static class Other {
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("v");
        Holder h = new Holder();

        f.setShort(h, (short) 42);
        if (f.getDouble(h) != 42.0d) {
            throw new AssertionError("expected 42.0 but got " + f.getDouble(h));
        }

        f.setShort(h, (short) -5);
        if (f.getDouble(h) != -5.0d) {
            throw new AssertionError("expected -5.0 but got " + f.getDouble(h));
        }

        boolean thrown = false;
        try {
            f.getDouble(new Other());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException for unrelated object");
        }

        thrown = false;
        try {
            f.getDouble(new Object());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException for Object instance");
        }
    }
}
