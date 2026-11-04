/*
 * @test
 * @summary Verify that Field.getInt on a public static long field throws IllegalArgumentException, which exercises UnsafeStaticLongFieldAccessorImpl.getInt, and that Field.getLong still works.
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {

    public static long longValue = 42L;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("longValue");

        long actual = field.getLong(null);
        if (actual != 42L) {
            throw new RuntimeException("Expected getLong to return 42 but was " + actual);
        }

        boolean thrown = false;
        try {
            field.getInt(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getInt on static long field");
        }
    }
}
