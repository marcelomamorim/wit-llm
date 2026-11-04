/*
 * @test
 * @summary Verify that Field.getChar on a static volatile double field throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static volatile double value = 1.5;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            field.getChar(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from Field.getChar on static volatile double");
        }

        if (field.getDouble(null) != 1.5) {
            throw new RuntimeException("Unexpected double value after getChar attempt");
        }
    }
}
