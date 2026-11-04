/*
 * @test
 * @summary Verify that Field.getByte on a static boolean field throws IllegalArgumentException, which exercises UnsafeStaticBooleanFieldAccessorImpl.getByte
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {

    public static boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");

        boolean value = f.getBoolean(null);
        if (!value) {
            throw new RuntimeException("getBoolean returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getByte(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getByte did not throw IllegalArgumentException");
        }
    }
}
