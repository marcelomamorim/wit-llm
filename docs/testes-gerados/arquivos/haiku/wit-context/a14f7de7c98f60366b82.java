/*
 * @test
 * @summary Verifies that Field.getLong on a static float field throws IllegalArgumentException via UnsafeStaticFloatFieldAccessorImpl.getLong
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {

    public static float value = 1.5f;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getField("value");

        float f = field.getFloat(null);
        if (f != 1.5f) {
            throw new AssertionError("getFloat returned unexpected value: " + f);
        }

        for (int i = 0; i < 2; i++) {
            boolean thrown = false;
            try {
                field.getLong(null);
            } catch (IllegalArgumentException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected IllegalArgumentException from getLong on float field");
            }
        }
    }
}
