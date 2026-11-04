/*
 * @test
 * @summary Verify Field.getInt on a volatile int field, which is served by UnsafeQualifiedIntegerFieldAccessorImpl.getInt
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile int value;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        h.value = 42;
        Field f = Holder.class.getField("value");

        if (f.getInt(h) != 42) {
            throw new RuntimeException("expected 42 but got " + f.getInt(h));
        }

        h.value = -7;
        if (f.getInt(h) != -7) {
            throw new RuntimeException("expected -7 after update");
        }

        Object boxed = f.get(h);
        if (!(boxed instanceof Integer) || ((Integer) boxed).intValue() != -7) {
            throw new RuntimeException("get() should return Integer -7 but got " + boxed);
        }

        try {
            f.getInt("not a Holder");
            throw new RuntimeException("expected IllegalArgumentException for wrong receiver type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
