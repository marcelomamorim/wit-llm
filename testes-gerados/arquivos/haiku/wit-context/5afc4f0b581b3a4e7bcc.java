/*
 * @test
 * @summary Verify that Field.getByte on a static float field throws IllegalArgumentException, exercising UnsafeStaticFloatFieldAccessorImpl.getByte through the public reflection API
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static float value = 1.5f;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("value");

        if (field.getFloat(null) != 1.5f) {
            throw new AssertionError("getFloat returned unexpected value");
        }

        try {
            field.getByte(null);
            throw new AssertionError("Expected IllegalArgumentException from getByte on float field");
        } catch (IllegalArgumentException expected) {
        }
    }
}
