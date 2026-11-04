/*
 * @test
 * @summary Verify that getFloat on a static Object field throws IllegalArgumentException while get still returns the stored value
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static Object objectField = "value";

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("objectField");

        Object stored = field.get(null);
        if (!"value".equals(stored)) {
            throw new AssertionError("get returned unexpected value: " + stored);
        }

        boolean thrown = false;
        try {
            field.getFloat(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getFloat on Object field did not throw IllegalArgumentException");
        }

        Object after = field.get(null);
        if (!"value".equals(after)) {
            throw new AssertionError("field changed after failed getFloat: " + after);
        }
    }
}
