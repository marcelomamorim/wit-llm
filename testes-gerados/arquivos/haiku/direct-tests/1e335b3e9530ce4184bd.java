/*
 * @test
 * @summary Field.getFloat on a static double field must throw IllegalArgumentException, while getDouble returns the stored value
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    public static double value = 1.5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");

        boolean thrown = false;
        try {
            f.getFloat(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getFloat did not throw IllegalArgumentException");
        }

        thrown = false;
        try {
            f.getFloat(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getFloat did not throw IllegalArgumentException on second call");
        }

        if (f.getDouble(null) != 1.5) {
            throw new AssertionError("getDouble returned unexpected value");
        }
    }
}
