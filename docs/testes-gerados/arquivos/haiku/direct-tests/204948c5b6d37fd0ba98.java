/*
 * @test
 * @summary Verifies observable short field read and write behavior through java.lang.reflect.Field, which dispatches to UnsafeShortFieldAccessorImpl
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {

    static class Holder {
        short s = 7;
        final short f = 3;
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();

        Field fs = Holder.class.getDeclaredField("s");
        fs.setAccessible(true);

        check(fs.getShort(h) == 7, "getShort initial value");
        check(((Short) fs.get(h)).shortValue() == 7, "get returns boxed Short");
        check(fs.getInt(h) == 7, "getInt widening");
        check(fs.getLong(h) == 7L, "getLong widening");
        check(fs.getFloat(h) == 7.0f, "getFloat widening");
        check(fs.getDouble(h) == 7.0d, "getDouble widening");

        fs.setShort(h, (short) -5);
        check(h.s == -5, "setShort stores value");
        check(fs.getShort(h) == -5, "getShort after setShort");

        fs.setByte(h, (byte) 9);
        check(h.s == 9, "setByte widens to short");

        fs.set(h, Short.valueOf((short) 11));
        check(h.s == 11, "set with Short");

        fs.set(h, Byte.valueOf((byte) 12));
        check(h.s == 12, "set with Byte");

        try {
            fs.set(h, "x");
            throw new RuntimeException("set with String should fail");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            fs.set(h, null);
            throw new RuntimeException("set with null should fail");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            fs.getBoolean(h);
            throw new RuntimeException("getBoolean on short field should fail");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        Field ff = Holder.class.getDeclaredField("f");
        ff.setAccessible(true);
        check(ff.getShort(h) == 3, "final field initial value");
        try {
            ff.setShort(h, (short) 1);
            throw new RuntimeException("setShort on final field should fail");
        } catch (IllegalAccessException expected) {
            // expected
        }
        check(ff.getShort(h) == 3, "final field unchanged after failed set");
    }
}
