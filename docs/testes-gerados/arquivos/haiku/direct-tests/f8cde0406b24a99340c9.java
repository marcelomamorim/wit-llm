/*
 * @test
 * @summary Verify that Field.getByte on a static non-primitive field throws IllegalArgumentException, which exercises UnsafeStaticObjectFieldAccessorImpl.getByte through the public reflection API
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static Object objField = "value";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("objField");

        Object v = f.get(null);
        if (!"value".equals(v)) {
            throw new RuntimeException("Unexpected value from get: " + v);
        }

        boolean thrown = false;
        try {
            f.getByte(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getByte on Object field");
        }
    }
}
