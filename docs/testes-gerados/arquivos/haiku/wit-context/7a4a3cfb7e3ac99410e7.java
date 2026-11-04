/*
 * @test
 * @summary Verifies that reading a static final short field as char through the public Field API throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    public static final short VALUE = 42;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticShortFieldAccessorImplWitupTest.class.getDeclaredField("VALUE");

        if (field.getShort(null) != 42) {
            throw new AssertionError("getShort returned unexpected value");
        }

        boolean thrown = false;
        try {
            field.getChar(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getChar on short field did not throw IllegalArgumentException");
        }
    }
}
