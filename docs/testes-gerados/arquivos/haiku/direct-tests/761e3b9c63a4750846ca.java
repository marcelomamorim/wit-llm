/*
 * @test
 * @summary Verify that getBoolean on a static final Object field is rejected with IllegalArgumentException
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {
    public static final Object OBJ_FIELD = "value";
    static final Object NULL_FIELD = null;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("OBJ_FIELD");
        f.setAccessible(true);

        if (!"value".equals(f.get(null))) {
            throw new AssertionError("get(null) should return the field value");
        }

        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getBoolean on Object field must throw IllegalArgumentException");
        }

        Field n = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("NULL_FIELD");
        n.setAccessible(true);
        thrown = false;
        try {
            n.getBoolean(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getBoolean on null Object field must throw IllegalArgumentException");
        }
    }
}
