/*
 * @test
 * @summary Field.getInt on a static Object field must throw IllegalArgumentException via the public reflection API
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static Object objField = "value";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("objField");
        try {
            int v = f.getInt(null);
            throw new AssertionError("Expected IllegalArgumentException, got: " + v);
        } catch (IllegalArgumentException expected) {
        }

        Object before = f.get(null);
        if (!"value".equals(before)) {
            throw new AssertionError("Field value changed unexpectedly: " + before);
        }
    }
}
