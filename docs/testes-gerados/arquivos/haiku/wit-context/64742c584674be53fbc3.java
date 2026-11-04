/*
 * @test
 * @summary Verify that reading a static double field as long through the public Field API throws IllegalArgumentException
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static double staticDouble = 1.5d;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("staticDouble");

        boolean thrown = false;
        try {
            field.getLong(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getLong on a double field");
        }

        Object value = field.get(null);
        if (!(value instanceof Double) || ((Double) value).doubleValue() != 1.5d) {
            throw new RuntimeException("Unexpected value from get: " + value);
        }
    }
}
