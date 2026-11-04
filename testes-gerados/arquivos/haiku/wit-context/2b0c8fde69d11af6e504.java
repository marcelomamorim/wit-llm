/*
 * @test
 * @summary Field.getByte on a static boolean field must throw IllegalArgumentException and getBoolean must still return the value
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static final boolean FLAG = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getField("FLAG");

        if (f.getBoolean(null) != true) {
            throw new AssertionError("getBoolean returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getByte(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getByte did not throw IllegalArgumentException");
        }
    }
}
