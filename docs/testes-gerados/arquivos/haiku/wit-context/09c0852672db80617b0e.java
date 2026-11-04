/*
 * @test
 * @summary Field.getByte on a static short field must throw IllegalArgumentException, reaching UnsafeStaticShortFieldAccessorImpl.getByte
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticShortFieldAccessorImplWitupTest {
    static short sval = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticShortFieldAccessorImplWitupTest.class.getDeclaredField("sval");
        if (f.getShort(null) != 42) {
            throw new RuntimeException("getShort mismatch");
        }
        try {
            byte b = f.getByte(null);
            throw new RuntimeException("expected IllegalArgumentException, got " + b);
        } catch (IllegalArgumentException expected) {
        }
    }
}
