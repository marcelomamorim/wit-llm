/*
 * @test
 * @summary Verify that Field.getFloat on a static boolean field throws IllegalArgumentException, as implemented by UnsafeStaticBooleanFieldAccessorImpl.getFloat
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {

    public static boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");

        if (!field.getBoolean(null)) {
            throw new RuntimeException("getBoolean should return the current value of flag");
        }

        try {
            float f = field.getFloat(null);
            throw new RuntimeException("getFloat should throw IllegalArgumentException but returned " + f);
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            field.getFloat(new Object());
            throw new RuntimeException("getFloat with non-null obj should throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        flag = false;
        try {
            field.getFloat(null);
            throw new RuntimeException("getFloat should throw after value change");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
