/*
 * @test
 * @summary Verifies that Field.getBoolean on a short field throws IllegalArgumentException, the observable public behavior that reaches UnsafeQualifiedShortFieldAccessorImpl.getBoolean
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static short sValue = 7;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedShortFieldAccessorImplWitupTest.class.getField("sValue");

        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Field.getBoolean on short field must throw IllegalArgumentException");
        }

        Object value = f.get(null);
        if (!(value instanceof Short) || ((Short) value).shortValue() != 7) {
            throw new AssertionError("Field.get on short field returned unexpected value: " + value);
        }
    }
}
