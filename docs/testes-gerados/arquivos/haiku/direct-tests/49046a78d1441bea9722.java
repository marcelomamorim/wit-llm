/*
 * @test
 * @summary Verify Field.getDouble on a volatile byte field widens the byte value (exercises UnsafeQualifiedByteFieldAccessorImpl.getDouble)
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public volatile byte b;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedByteFieldAccessorImplWitupTest t = new UnsafeQualifiedByteFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedByteFieldAccessorImplWitupTest.class.getField("b");

        t.b = 0;
        check(f.getDouble(t) == 0.0d, "zero");

        t.b = 42;
        check(f.getDouble(t) == 42.0d, "positive");

        t.b = -5;
        check(f.getDouble(t) == -5.0d, "negative");

        t.b = Byte.MAX_VALUE;
        check(f.getDouble(t) == 127.0d, "max");

        t.b = Byte.MIN_VALUE;
        check(f.getDouble(t) == -128.0d, "min");

        t.b = 7;
        check(f.getDouble(t) == f.getByte(t), "consistent with getByte");

        try {
            f.getDouble("not a declaring instance");
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void check(boolean cond, String what) {
        if (!cond) {
            throw new RuntimeException("Failed: " + what);
        }
    }
}
