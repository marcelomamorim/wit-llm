/*
 * @test
 * @summary Verify that Field.getChar on a static long field is rejected through the public reflection API, which dispatches to UnsafeStaticLongFieldAccessorImpl.getChar
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {
    static long value = 42L;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("value");
        try {
            f.getChar(null);
            throw new AssertionError("expected IllegalArgumentException from getChar on long field");
        } catch (IllegalArgumentException expected) {
        }
        if (f.getLong(null) != 42L) {
            throw new AssertionError("getLong returned unexpected value");
        }
    }
}
