/*
 * @test
 * @summary Field.getInt on a double field must throw IllegalArgumentException, while getDouble returns the stored value
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public double value = 1.5;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedDoubleFieldAccessorImplWitupTest obj = new UnsafeQualifiedDoubleFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedDoubleFieldAccessorImplWitupTest.class.getField("value");

        if (f.getDouble(obj) != 1.5d) {
            throw new AssertionError("getDouble should return 1.5");
        }

        boolean thrown = false;
        try {
            f.getInt(obj);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getInt on a double field should throw IllegalArgumentException");
        }
    }
}
