/*
 * @test
 * @summary Field.getBoolean on a short field must throw IllegalArgumentException
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class Holder {
        short s = 7;
        final short fs = 9;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        check("s", h);
        check("fs", h);
    }

    static void check(String name, Holder h) throws Exception {
        Field f = Holder.class.getDeclaredField(name);
        f.setAccessible(true);
        try {
            f.getBoolean(h);
            throw new RuntimeException("Expected IllegalArgumentException for field " + name);
        } catch (IllegalArgumentException expected) {
            // expected: getBoolean on a short field is not a valid widening
        }
    }
}
