/*
 * @test
 * @summary Verify Field.getChar on a static short field throws IllegalArgumentException (UnsafeStaticShortFieldAccessorImpl.getChar)
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticShortFieldAccessorImplWitupTest {
    static short s = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticShortFieldAccessorImplWitupTest.class.getDeclaredField("s");
        if (f.getShort(null) != 42) {
            throw new RuntimeException("getShort returned unexpected value");
        }
        try {
            f.getChar(null);
            throw new RuntimeException("expected IllegalArgumentException from getChar");
        } catch (IllegalArgumentException expected) {
            // expected behavior
        }
    }
}
