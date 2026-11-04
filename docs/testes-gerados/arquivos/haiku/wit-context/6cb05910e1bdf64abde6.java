/*
 * @test
 * @summary Field.getByte on a static final short field must throw IllegalArgumentException (narrowing short to byte is not allowed)
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    public static final short SHORT_VALUE = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticShortFieldAccessorImplWitupTest.class.getField("SHORT_VALUE");

        boolean thrown = false;
        try {
            f.getByte(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getByte on short field");
        }

        thrown = false;
        try {
            f.getByte(new Object());
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getByte with non-null obj");
        }

        if (f.getShort(null) != 42) {
            throw new RuntimeException("getShort must still return the field value");
        }
    }
}
