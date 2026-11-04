/*
 * @test
 * @summary Field.getInt on a static long field must throw IllegalArgumentException via UnsafeStaticLongFieldAccessorImpl.getInt
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {

    public static long longValue = 42L;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("longValue");

        // getLong on the same accessor path must still return the stored value
        long l = field.getLong(null);
        if (l != 42L) {
            throw new RuntimeException("Expected getLong to return 42 but got " + l);
        }

        // getInt must reject a long field with IllegalArgumentException
        boolean thrown = false;
        try {
            field.getInt(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getInt on static long field");
        }

        // Repeated call must behave the same (no state change)
        thrown = false;
        try {
            field.getInt(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException on second getInt call");
        }
    }
}
