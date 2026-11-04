/*
 * @test
 * @summary Verify that Field.getByte on a volatile short field throws IllegalArgumentException and that Field.get still returns the short value
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {
    public volatile short s = 42;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedShortFieldAccessorImplWitupTest t = new UnsafeQualifiedShortFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedShortFieldAccessorImplWitupTest.class.getField("s");

        Object value = f.get(t);
        if (!(value instanceof Short) || ((Short) value).shortValue() != 42) {
            throw new RuntimeException("Field.get returned unexpected value: " + value);
        }

        try {
            byte b = f.getByte(t);
            throw new RuntimeException("Expected IllegalArgumentException from getByte, got: " + b);
        } catch (IllegalArgumentException expected) {
        }
    }
}
