/*
 * @test
 * @summary Verifies that Field.getFloat on a short instance field reaches UnsafeShortFieldAccessorImpl.getFloat and widens the stored short value
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {
    public short value;

    public static void main(String[] args) throws Exception {
        UnsafeShortFieldAccessorImplWitupTest t = new UnsafeShortFieldAccessorImplWitupTest();
        Field f = UnsafeShortFieldAccessorImplWitupTest.class.getField("value");

        t.value = -5;
        check(f.getFloat(t) == -5.0f, "negative value widened to float");

        t.value = Short.MAX_VALUE;
        check(f.getFloat(t) == 32767.0f, "MAX_VALUE widened to float");

        t.value = Short.MIN_VALUE;
        check(f.getFloat(t) == -32768.0f, "MIN_VALUE widened to float");

        t.value = 0;
        check(f.getFloat(t) == 0.0f, "zero widened to float");

        f.set(t, Short.valueOf((short) 7));
        check(f.getFloat(t) == 7.0f, "value set via set(Short) read as float");

        f.setShort(t, (short) 1234);
        check(f.getFloat(t) == 1234.0f, "value set via setShort read as float");
        check(f.getFloat(t) == (float) f.getInt(t), "getFloat consistent with getInt");
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("Failed: " + msg);
        }
    }
}
