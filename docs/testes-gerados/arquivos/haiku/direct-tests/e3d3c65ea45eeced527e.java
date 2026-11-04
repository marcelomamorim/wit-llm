/*
 * @test
 * @summary Verifies that Field.getByte on a static long field reaches UnsafeStaticLongFieldAccessorImpl.getByte, which must throw IllegalArgumentException and leave the value unchanged
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {
    static long staticLong = 42L;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("staticLong");

        if (f.getLong(null) != 42L) {
            throw new RuntimeException("Sanity check failed: getLong returned " + f.getLong(null));
        }

        try {
            f.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException from getByte(null)");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            f.getByte(new Object());
            throw new RuntimeException("Expected IllegalArgumentException from getByte(Object)");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        if (f.getLong(null) != 42L) {
            throw new RuntimeException("Value changed after getByte: " + f.getLong(null));
        }
    }
}
