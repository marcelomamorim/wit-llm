/*
 * @test
 * @summary Verify that Field.getInt on a static float field throws IllegalArgumentException via UnsafeStaticFloatFieldAccessorImpl.getInt
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static float value = 1.5f;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getField("value");
        boolean thrown = false;
        try {
            f.getInt(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getInt on float field");
        }
        if (f.getFloat(null) != 1.5f) {
            throw new AssertionError("float value changed unexpectedly");
        }
    }
}
