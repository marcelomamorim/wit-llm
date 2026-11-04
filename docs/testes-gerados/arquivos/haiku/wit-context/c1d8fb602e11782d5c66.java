/*
 * @test
 * @summary Verifies Field.getLong on short fields, which reaches UnsafeShortFieldAccessorImpl.getLong through public reflection API
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static class Holder {
        public short s;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("s");
        Holder h = new Holder();

        h.s = -123;
        check(f.getLong(h) == -123L, "negative value widened to long");
        check(f.getLong(h) == f.getShort(h), "getLong equals getShort widened");

        h.s = Short.MAX_VALUE;
        check(f.getLong(h) == 32767L, "max short value");

        h.s = Short.MIN_VALUE;
        check(f.getLong(h) == -32768L, "min short value");

        h.s = 0;
        check(f.getLong(h) == 0L, "zero value");

        h.s = 42;
        h.s = 43;
        check(f.getLong(h) == 43L, "reflects latest state");

        boolean thrown = false;
        try {
            f.getLong(new Object());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "wrong receiver type must throw IllegalArgumentException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
