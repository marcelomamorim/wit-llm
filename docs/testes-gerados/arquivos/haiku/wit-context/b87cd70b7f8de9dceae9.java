/*
 * @test
 * @summary Verifies that Field.getChar on a static short field throws IllegalArgumentException, as UnsafeStaticShortFieldAccessorImpl.getChar does
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticShortFieldAccessorImplWitupTest {
    public static short sval = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticShortFieldAccessorImplWitupTest.class.getField("sval");

        if (f.getShort(null) != 42) {
            throw new RuntimeException("getShort sanity check failed");
        }

        boolean thrownNull = false;
        try {
            f.getChar(null);
        } catch (IllegalArgumentException expected) {
            thrownNull = true;
        }
        if (!thrownNull) {
            throw new RuntimeException("getChar(null) did not throw IllegalArgumentException");
        }

        boolean thrownObj = false;
        try {
            f.getChar(new Object());
        } catch (IllegalArgumentException expected) {
            thrownObj = true;
        }
        if (!thrownObj) {
            throw new RuntimeException("getChar(Object) did not throw IllegalArgumentException");
        }
    }
}
