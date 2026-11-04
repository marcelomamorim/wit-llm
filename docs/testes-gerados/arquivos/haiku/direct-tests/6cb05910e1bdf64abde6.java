/*
 * @test
 * @summary Field.getByte on a static short field must throw IllegalArgumentException (narrowing not allowed), reaching UnsafeQualifiedStaticShortFieldAccessorImpl.getByte through public API
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    static short shortValue = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticShortFieldAccessorImplWitupTest.class.getDeclaredField("shortValue");

        boolean thrown = false;
        try {
            f.getByte(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getByte on short field");
        }

        if (f.getShort(null) != 42) {
            throw new RuntimeException("getShort returned unexpected value");
        }
    }
}
