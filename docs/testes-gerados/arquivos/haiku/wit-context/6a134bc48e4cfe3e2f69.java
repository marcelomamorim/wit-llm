/*
 * @test
 * @summary Verify that Field.getChar on a double field throws IllegalArgumentException, reaching UnsafeDoubleFieldAccessorImpl.getChar through the public API
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public double value = 1.5;

    public static void main(String[] args) throws Exception {
        UnsafeDoubleFieldAccessorImplWitupTest obj = new UnsafeDoubleFieldAccessorImplWitupTest();
        Field field = UnsafeDoubleFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            field.getChar(obj);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getChar on a double field");
        }

        if (field.getDouble(obj) != 1.5) {
            throw new AssertionError("getDouble should still return the stored value");
        }
    }
}
