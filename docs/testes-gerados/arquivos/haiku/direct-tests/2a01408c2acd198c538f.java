/*
 * @test
 * @summary Field.getDouble on a static Object field must throw IllegalArgumentException
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    static Object objField = "value";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("objField");

        try {
            double d = f.getDouble(null);
            throw new AssertionError("expected IllegalArgumentException, got " + d);
        } catch (IllegalArgumentException expected) {
        }

        try {
            double d = f.getDouble(new Object());
            throw new AssertionError("expected IllegalArgumentException, got " + d);
        } catch (IllegalArgumentException expected) {
        }
    }
}
