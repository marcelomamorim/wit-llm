/*
 * @test
 * @summary Verify that Field.getShort on a static double field throws IllegalArgumentException, as implemented by UnsafeStaticDoubleFieldAccessorImpl.getShort
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static double value = 1.5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");

        if (f.getDouble(null) != 1.5) {
            throw new RuntimeException("getDouble returned unexpected value");
        }

        boolean thrownNull = false;
        try {
            f.getShort(null);
        } catch (IllegalArgumentException e) {
            thrownNull = true;
        }
        if (!thrownNull) {
            throw new RuntimeException("getShort(null) did not throw IllegalArgumentException");
        }

        boolean thrownObj = false;
        try {
            f.getShort(new UnsafeStaticDoubleFieldAccessorImplWitupTest());
        } catch (IllegalArgumentException e) {
            thrownObj = true;
        }
        if (!thrownObj) {
            throw new RuntimeException("getShort(obj) did not throw IllegalArgumentException");
        }
    }
}
