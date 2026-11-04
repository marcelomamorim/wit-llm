/*
 * @test
 * @summary Checks that Field.getBoolean rejects a static long field with IllegalArgumentException and that Field.get returns the boxed long value
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static long value = 42L;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("value");

        boolean thrown = false;
        try {
            field.getBoolean(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getBoolean on long field did not throw IllegalArgumentException");
        }

        Object boxed = field.get(null);
        if (!(boxed instanceof Long) || ((Long) boxed).longValue() != 42L) {
            throw new RuntimeException("Unexpected value from Field.get: " + boxed);
        }
    }
}
