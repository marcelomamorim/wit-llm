/*
 * @test
 * @summary Field.getChar on a double field must throw IllegalArgumentException, which is the observable behavior of UnsafeDoubleFieldAccessorImpl.getChar
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public double value = 1.5d;

    public static void main(String[] args) throws Exception {
        UnsafeDoubleFieldAccessorImplWitupTest obj = new UnsafeDoubleFieldAccessorImplWitupTest();
        Field f = UnsafeDoubleFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            f.getChar(obj);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getChar on double field");
        }

        if (f.getDouble(obj) != 1.5d) {
            throw new RuntimeException("getDouble returned unexpected value");
        }
    }
}
