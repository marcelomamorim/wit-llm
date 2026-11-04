/*
 * @test
 * @summary Verify that Field.getBoolean on a static long field, which dispatches to UnsafeStaticLongFieldAccessorImpl.getBoolean, throws IllegalArgumentException
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {
    public static long value = 42L;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticLongFieldAccessorImplWitupTest.class.getField("value");

        Object boxed = field.get(null);
        if (!(boxed instanceof Long) || ((Long) boxed).longValue() != 42L) {
            throw new AssertionError("Unexpected value from get: " + boxed);
        }

        boolean thrown = false;
        try {
            field.getBoolean(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getBoolean on static long field did not throw IllegalArgumentException");
        }
    }
}
