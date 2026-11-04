/*
 * @test
 * @summary Verifies that Field.getInt on a static double field throws IllegalArgumentException, which is the observable behavior of UnsafeStaticDoubleFieldAccessorImpl.getInt
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static double value = 1.5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");

        boolean thrown = false;
        try {
            f.getInt(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getInt on double field");
        }

        if (f.getDouble(null) != 1.5) {
            throw new RuntimeException("getDouble returned unexpected value");
        }
    }
}
