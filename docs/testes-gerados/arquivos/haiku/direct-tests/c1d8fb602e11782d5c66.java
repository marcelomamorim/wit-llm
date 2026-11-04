/*
 * @test
 * @summary Verifies that Field.getLong on a short field widens the stored value through UnsafeShortFieldAccessorImpl.getLong
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {
    private short s;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeShortFieldAccessorImplWitupTest.class.getDeclaredField("s");
        f.setAccessible(true);
        UnsafeShortFieldAccessorImplWitupTest t = new UnsafeShortFieldAccessorImplWitupTest();

        t.s = -5;
        check(f.getLong(t) == -5L, "negative value widened");

        t.s = 0;
        check(f.getLong(t) == 0L, "zero value");

        t.s = Short.MAX_VALUE;
        check(f.getLong(t) == 32767L, "max short value");

        t.s = Short.MIN_VALUE;
        check(f.getLong(t) == -32768L, "min short value");

        f.set(t, Short.valueOf((short) 1234));
        check(f.getLong(t) == 1234L, "value set via Field.set");
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("Failed: " + msg);
        }
    }
}
