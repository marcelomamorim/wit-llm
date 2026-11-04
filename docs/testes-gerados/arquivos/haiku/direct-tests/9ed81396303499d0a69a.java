/*
 * @test
 * @summary Verifies Field.getShort on a volatile byte field returns the sign-extended byte value and rejects objects of an unrelated type
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public volatile byte value;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedByteFieldAccessorImplWitupTest t = new UnsafeQualifiedByteFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedByteFieldAccessorImplWitupTest.class.getField("value");

        t.value = -5;
        check(f.getShort(t) == (short) -5, "negative value not sign-extended");

        t.value = Byte.MAX_VALUE;
        check(f.getShort(t) == (short) 127, "Byte.MAX_VALUE mismatch");

        t.value = Byte.MIN_VALUE;
        check(f.getShort(t) == (short) -128, "Byte.MIN_VALUE mismatch");

        t.value = 0;
        check(f.getShort(t) == 0, "zero mismatch");

        f.setByte(t, (byte) 42);
        check(f.getShort(t) == (short) 42, "value written via setByte not observed by getShort");
        check(f.getShort(t) == f.getByte(t), "getShort differs from getByte");

        boolean thrown = false;
        try {
            f.getShort(new Object());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for object of unrelated type");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
