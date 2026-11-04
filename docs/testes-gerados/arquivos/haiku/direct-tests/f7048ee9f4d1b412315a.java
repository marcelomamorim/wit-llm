/*
 * @test
 * @summary Verifies that Field.getInt on a static boolean field reaches UnsafeStaticBooleanFieldAccessorImpl.getInt and throws IllegalArgumentException, while getBoolean still works
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {

    public static boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");

        if (!f.getBoolean(null)) {
            throw new RuntimeException("getBoolean should return true for flag");
        }

        boolean thrown = false;
        try {
            f.getInt(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getInt on a static boolean field must throw IllegalArgumentException");
        }
    }
}
