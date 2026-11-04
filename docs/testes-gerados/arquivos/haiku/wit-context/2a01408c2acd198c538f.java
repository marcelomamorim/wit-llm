/*
 * @test
 * @summary Verifies that Field.getDouble on a static Object field, which is served by UnsafeStaticObjectFieldAccessorImpl, throws IllegalArgumentException
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static Object objectValue = "witup";

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("objectValue");

        try {
            field.getDouble(null);
            throw new AssertionError("Expected IllegalArgumentException from getDouble on Object field");
        } catch (IllegalArgumentException expected) {
            // expected path: newGetDoubleIllegalArgumentException()
        }

        Object value = field.get(null);
        if (!"witup".equals(value)) {
            throw new AssertionError("Unexpected value from get: " + value);
        }
    }
}
