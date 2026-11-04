/*
 * @test
 * @summary Verifies that Field accessors reject getShort on a static boolean field, reaching UnsafeStaticBooleanFieldAccessorImpl.getShort through the public reflection API
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {
    public static boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getField("flag");

        try {
            f.getShort(null);
            throw new AssertionError("expected IllegalArgumentException from getShort");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getShort(new UnsafeStaticBooleanFieldAccessorImplWitupTest());
            throw new AssertionError("expected IllegalArgumentException from getShort with instance");
        } catch (IllegalArgumentException expected) {
        }

        if (!f.getBoolean(null)) {
            throw new AssertionError("getBoolean should return true");
        }

        try {
            f.getByte(null);
            throw new AssertionError("expected IllegalArgumentException from getByte");
        } catch (IllegalArgumentException expected) {
        }
    }
}
