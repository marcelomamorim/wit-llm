/*
 * @test
 * @summary Verify that Field.getChar on a static long field throws IllegalArgumentException and that getLong still works
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {
    public static long value = 42L;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticLongFieldAccessorImplWitupTest.class.getField("value");
        if (f.getLong(null) != 42L) {
            throw new RuntimeException("getLong returned unexpected value");
        }
        boolean thrown = false;
        try {
            f.getChar(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException from getChar");
        }
    }
}
