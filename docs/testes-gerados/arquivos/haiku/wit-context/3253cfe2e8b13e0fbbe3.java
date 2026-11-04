/*
 * @test
 * @summary Verifies that reading a double field as float through the public Field API throws IllegalArgumentException, which exercises UnsafeDoubleFieldAccessorImpl.getFloat
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {
    public double value = 1.5;

    public static void main(String[] args) throws Exception {
        UnsafeDoubleFieldAccessorImplWitupTest holder = new UnsafeDoubleFieldAccessorImplWitupTest();
        Field field = UnsafeDoubleFieldAccessorImplWitupTest.class.getField("value");

        if (field.getDouble(holder) != 1.5d) {
            throw new AssertionError("getDouble sanity check failed");
        }

        boolean thrown = false;
        try {
            field.getFloat(holder);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getFloat on a double field");
        }
    }
}
