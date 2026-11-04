/*
 * @test
 * @summary Verify that Field.getInt widens byte field values as observed through the public reflection API
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    private byte b;

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedByteFieldAccessorImplWitupTest.class.getDeclaredField("b");
        f.setAccessible(true);

        UnsafeQualifiedByteFieldAccessorImplWitupTest obj = new UnsafeQualifiedByteFieldAccessorImplWitupTest();

        obj.b = 0;
        check(f.getInt(obj) == 0, "zero value expected 0");

        obj.b = 42;
        check(f.getInt(obj) == 42, "positive value expected 42");

        obj.b = -5;
        check(f.getInt(obj) == -5, "negative value expected -5");

        obj.b = Byte.MAX_VALUE;
        check(f.getInt(obj) == 127, "MAX_VALUE expected 127");

        obj.b = Byte.MIN_VALUE;
        check(f.getInt(obj) == -128, "MIN_VALUE expected -128");

        boolean npe = false;
        try {
            f.getInt(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null receiver for instance field should throw NullPointerException");
    }
}
