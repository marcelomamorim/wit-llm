/*
 * @test
 * @summary Verify Field.getShort on a volatile short field (Unsafe-qualified accessor) returns the stored value and rejects objects of an unrelated type
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile short value = 42;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("value");
        Holder h = new Holder();

        short first = f.getShort(h);
        if (first != 42) {
            throw new AssertionError("expected 42 but was " + first);
        }

        h.value = 7;
        short second = f.getShort(h);
        if (second != 7) {
            throw new AssertionError("expected 7 but was " + second);
        }

        int asInt = f.getInt(h);
        if (asInt != 7) {
            throw new AssertionError("expected getInt 7 but was " + asInt);
        }

        try {
            f.getShort(new Object());
            throw new AssertionError("expected IllegalArgumentException for unrelated object");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getShort("text");
            throw new AssertionError("expected IllegalArgumentException for String object");
        } catch (IllegalArgumentException expected) {
        }
    }
}
