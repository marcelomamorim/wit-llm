/*
 * @test
 * @summary Verifies that Field.getFloat on a static final double field throws IllegalArgumentException, as implemented by UnsafeQualifiedStaticDoubleFieldAccessorImpl.getFloat
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {
    public static final double VALUE = 1.5d;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("VALUE");

        if (f.getDouble(null) != 1.5d) {
            throw new RuntimeException("getDouble returned unexpected value");
        }

        boolean thrown = false;
        try {
            float r = f.getFloat(null);
            throw new RuntimeException("getFloat did not throw, returned " + r);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException expected from getFloat");
        }
    }
}
