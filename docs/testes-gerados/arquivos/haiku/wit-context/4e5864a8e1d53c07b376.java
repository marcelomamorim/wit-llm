/*
 * @test
 * @summary Verifies that Field.getBoolean on a static float field throws IllegalArgumentException, reaching the UnsafeStaticFloatFieldAccessorImpl.getBoolean path through the public reflection API
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {

    public static float value = 1.5f;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("value");

        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from Field.getBoolean on float field");
        }

        if (f.getFloat(null) != 1.5f) {
            throw new RuntimeException("Float field value changed unexpectedly");
        }
    }
}
