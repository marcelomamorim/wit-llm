/*
 * @test
 * @summary Verifies that Field.getBoolean on a static double field throws IllegalArgumentException and that getDouble/get still work
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    static double value = 1.5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");

        try {
            f.getBoolean(null);
            throw new AssertionError("expected IllegalArgumentException from getBoolean");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getBoolean(null);
            throw new AssertionError("expected IllegalArgumentException from repeated getBoolean");
        } catch (IllegalArgumentException expected) {
        }

        if (f.getDouble(null) != 1.5) {
            throw new AssertionError("getDouble returned unexpected value");
        }

        Object boxed = f.get(null);
        if (!(boxed instanceof Double) || ((Double) boxed).doubleValue() != 1.5) {
            throw new AssertionError("get returned unexpected value: " + boxed);
        }
    }
}
