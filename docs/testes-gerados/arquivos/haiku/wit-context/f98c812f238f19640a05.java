/*
 * @test
 * @summary Verifies that reading a double field as short through the public Field API throws IllegalArgumentException
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public double value = 1.5;

    public static void main(String[] args) throws Exception {
        UnsafeDoubleFieldAccessorImplWitupTest target = new UnsafeDoubleFieldAccessorImplWitupTest();
        Field field = UnsafeDoubleFieldAccessorImplWitupTest.class.getField("value");

        if (field.getDouble(target) != 1.5d) {
            throw new AssertionError("unexpected double value");
        }

        boolean thrown = false;
        try {
            field.getShort(target);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getShort on a double field");
        }
    }
}
