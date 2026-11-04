/*
 * @test
 * @summary Verify that Field.getInt on a static Object field throws IllegalArgumentException via UnsafeStaticObjectFieldAccessorImpl
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    static Object objField = "value";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("objField");
        f.setAccessible(true);

        boolean thrown = false;
        try {
            f.getInt(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getInt on Object field");
        }

        thrown = false;
        try {
            f.getInt(new Object());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getInt with non-null obj");
        }

        Object value = f.get(null);
        if (!"value".equals(value)) {
            throw new RuntimeException("Field value changed unexpectedly: " + value);
        }
    }
}
