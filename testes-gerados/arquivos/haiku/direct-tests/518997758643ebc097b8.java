/*
 * @test
 * @summary Verifies observable int field access through java.lang.reflect.Field, which reaches UnsafeIntegerFieldAccessorImpl.getInt
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static class Holder {
        public int value;
        public String text;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        h.value = 42;
        Field f = Holder.class.getField("value");

        if (f.getInt(h) != 42) {
            throw new RuntimeException("getInt returned unexpected value");
        }

        Object boxed = f.get(h);
        if (!(boxed instanceof Integer) || ((Integer) boxed).intValue() != 42) {
            throw new RuntimeException("get returned unexpected value: " + boxed);
        }

        if (f.getLong(h) != 42L) {
            throw new RuntimeException("getLong returned unexpected value");
        }

        f.setInt(h, 7);
        if (f.getInt(h) != 7 || h.value != 7) {
            throw new RuntimeException("getInt did not observe setInt update");
        }

        try {
            f.getInt("not a Holder");
            throw new RuntimeException("expected IllegalArgumentException for wrong object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            f.getBoolean(h);
            throw new RuntimeException("expected IllegalArgumentException for getBoolean on int field");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
