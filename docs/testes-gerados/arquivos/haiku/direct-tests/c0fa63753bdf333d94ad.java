/*
 * @test
 * @summary Verify that Field.getDouble on a short instance field widens the stored value to double
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {
    public short s;

    public static void main(String[] args) throws Exception {
        UnsafeShortFieldAccessorImplWitupTest o = new UnsafeShortFieldAccessorImplWitupTest();
        Field f = UnsafeShortFieldAccessorImplWitupTest.class.getField("s");

        o.s = 1234;
        check(f.getDouble(o) == 1234.0, "positive value widened to double");

        o.s = -5;
        check(f.getDouble(o) == -5.0, "negative value widened to double");

        o.s = Short.MAX_VALUE;
        check(f.getDouble(o) == 32767.0, "max short widened to double");

        o.s = Short.MIN_VALUE;
        check(f.getDouble(o) == -32768.0, "min short widened to double");

        o.s = 0;
        check(f.getDouble(o) == 0.0, "zero widened to double");

        boolean thrown = false;
        try {
            f.getDouble("not an instance of the declaring class");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "wrong receiver type throws IllegalArgumentException");
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("FAILED: " + msg);
        }
    }
}
