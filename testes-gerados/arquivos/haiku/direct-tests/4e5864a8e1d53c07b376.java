/*
 * @test
 * @summary Verify that reading a static float field as boolean through java.lang.reflect.Field is rejected with IllegalArgumentException, while getFloat still returns the stored value
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static float value = 1.5f;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getField("value");

        if (field.getFloat(null) != 1.5f) {
            throw new AssertionError("getFloat returned unexpected value");
        }

        boolean thrown = false;
        try {
            field.getBoolean(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getBoolean on static float field did not throw IllegalArgumentException");
        }
    }
}
