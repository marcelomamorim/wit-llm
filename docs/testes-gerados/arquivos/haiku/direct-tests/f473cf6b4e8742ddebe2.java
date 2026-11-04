/*
 * @test
 * @summary Verify that Field.getBoolean on a static Object field throws IllegalArgumentException via the public reflection API
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static Object obj = "value";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getField("obj");
        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getBoolean on static Object field");
        }
    }
}
