/*
 * @test
 * @summary Verifies that Field.getFloat on a static Object field throws IllegalArgumentException, exercising UnsafeStaticObjectFieldAccessorImpl.getFloat through the public reflection API
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {
    public static Object objField = "value";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("objField");
        boolean thrown = false;
        try {
            f.getFloat(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getFloat on Object field");
        }
    }
}
