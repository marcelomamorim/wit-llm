/*
 * @test
 * @summary Verify Field.getFloat on short fields, which reaches the short accessor path whose getFloat returns getShort(obj) widened to float
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public short instanceValue;
    public static short staticValue;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedShortFieldAccessorImplWitupTest t = new UnsafeQualifiedShortFieldAccessorImplWitupTest();

        Field inst = UnsafeQualifiedShortFieldAccessorImplWitupTest.class.getField("instanceValue");

        t.instanceValue = 42;
        check(inst.getFloat(t) == 42.0f, "positive value");

        t.instanceValue = -1;
        check(inst.getFloat(t) == -1.0f, "negative value");

        t.instanceValue = Short.MIN_VALUE;
        check(inst.getFloat(t) == -32768.0f, "Short.MIN_VALUE");

        t.instanceValue = Short.MAX_VALUE;
        check(inst.getFloat(t) == 32767.0f, "Short.MAX_VALUE");

        Field stat = UnsafeQualifiedShortFieldAccessorImplWitupTest.class.getField("staticValue");
        staticValue = -5;
        check(stat.getFloat(null) == -5.0f, "static field");
    }

    private static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("Unexpected result for: " + what);
        }
    }
}
