/*
 * @test
 * @summary Verifies that Field.getDouble on a static final Object field throws IllegalArgumentException and that Field.get still returns the value
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {
    public static final Object VALUE = "x";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("VALUE");

        boolean thrown = false;
        try {
            f.getDouble(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getDouble on Object field");
        }

        Object v = f.get(null);
        if (!"x".equals(v)) {
            throw new RuntimeException("Unexpected value from get: " + v);
        }
    }
}
