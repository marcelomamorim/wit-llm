/*
 * @test
 * @summary Checks Field.getFloat on a volatile byte field, which is served by UnsafeQualifiedByteFieldAccessorImpl.getFloat
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    volatile byte value;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedByteFieldAccessorImplWitupTest t = new UnsafeQualifiedByteFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedByteFieldAccessorImplWitupTest.class.getDeclaredField("value");

        t.value = 42;
        check(f.getFloat(t) == 42.0f, "positive byte value not widened to float");

        t.value = -7;
        check(f.getFloat(t) == -7.0f, "negative byte value not widened to float");

        t.value = Byte.MIN_VALUE;
        check(f.getFloat(t) == -128.0f, "Byte.MIN_VALUE not returned as float");

        t.value = Byte.MAX_VALUE;
        check(f.getFloat(t) == 127.0f, "Byte.MAX_VALUE not returned as float");

        boolean thrown = false;
        try {
            f.getFloat(new Object());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "wrong receiver type did not throw IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
