/*
 * @test
 * @summary Verify that Field.getLong on a static volatile Object field throws IllegalArgumentException, and that Field.get still returns the stored value
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    static volatile Object value = "x";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("value");

        Object got = f.get(null);
        if (!"x".equals(got)) {
            throw new RuntimeException("Field.get returned unexpected value: " + got);
        }

        boolean thrownWithNull = false;
        try {
            f.getLong(null);
        } catch (IllegalArgumentException expected) {
            thrownWithNull = true;
        }
        if (!thrownWithNull) {
            throw new RuntimeException("getLong(null) did not throw IllegalArgumentException");
        }

        boolean thrownWithObject = false;
        try {
            f.getLong(new Object());
        } catch (IllegalArgumentException expected) {
            thrownWithObject = true;
        }
        if (!thrownWithObject) {
            throw new RuntimeException("getLong(Object) did not throw IllegalArgumentException");
        }
    }
}
