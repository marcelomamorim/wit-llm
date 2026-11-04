/*
 * @test
 * @summary Verify that Field.getInt on a static float field is rejected with IllegalArgumentException, exercising UnsafeStaticFloatFieldAccessorImpl.getInt through the public reflection API
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {

    public static float value = 1.5f;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            field.getInt(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getInt on static float field");
        }

        float f = field.getFloat(null);
        if (f != 1.5f) {
            throw new RuntimeException("Unexpected getFloat result: " + f);
        }
    }
}
