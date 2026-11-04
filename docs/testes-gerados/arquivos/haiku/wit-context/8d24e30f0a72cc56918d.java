/*
 * @test
 * @summary Verify Field.getLong on int fields (served by UnsafeIntegerFieldAccessorImpl.getLong) returns widened values and rejects objects of an unrelated type
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static class Holder {
        public int value;
    }

    public static class Other {
        public int other;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("value");

        Holder h = new Holder();
        h.value = 42;
        if (f.getLong(h) != 42L) {
            throw new RuntimeException("getLong returned unexpected value for 42: " + f.getLong(h));
        }

        h.value = -7;
        if (f.getLong(h) != -7L) {
            throw new RuntimeException("getLong returned unexpected value for -7");
        }

        h.value = Integer.MAX_VALUE;
        if (f.getLong(h) != (long) Integer.MAX_VALUE) {
            throw new RuntimeException("getLong did not widen Integer.MAX_VALUE correctly");
        }

        h.value = Integer.MIN_VALUE;
        if (f.getLong(h) != (long) Integer.MIN_VALUE) {
            throw new RuntimeException("getLong did not widen Integer.MIN_VALUE correctly");
        }

        boolean thrown = false;
        try {
            f.getLong(new Other());
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for object of unrelated type");
        }

        thrown = false;
        try {
            f.getLong("not a holder");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for String argument");
        }
    }
}
