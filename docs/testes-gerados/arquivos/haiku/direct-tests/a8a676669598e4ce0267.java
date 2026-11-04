/*
 * @test
 * @summary Verify that Field.getChar on a static double field is rejected by the UnsafeStaticDoubleFieldAccessorImpl accessor
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    public static double value = 3.5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");

        if (f.getDouble(null) != 3.5) {
            throw new RuntimeException("getDouble control check failed");
        }

        try {
            f.getChar(null);
            throw new RuntimeException("getChar(null) did not throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getChar(new Object());
            throw new RuntimeException("getChar(Object) did not throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }

        if (f.getDouble(null) != 3.5) {
            throw new RuntimeException("field value changed after getChar calls");
        }
    }
}
