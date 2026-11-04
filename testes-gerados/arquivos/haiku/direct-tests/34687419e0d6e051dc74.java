/*
 * @test
 * @summary Field.getInt on a volatile long field must throw IllegalArgumentException (widening not allowed)
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile long value = 42L;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getField("value");

        if (f.getLong(h) != 42L) {
            throw new RuntimeException("getLong returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getInt(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getInt on long field did not throw IllegalArgumentException");
        }

        thrown = false;
        try {
            f.getInt(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("repeated getInt did not throw IllegalArgumentException");
        }

        if (f.getLong(h) != 42L) {
            throw new RuntimeException("field value changed after failed getInt");
        }
    }
}
