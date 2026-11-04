/*
 * @test
 * @summary Verify that Field.getShort on a static long field throws IllegalArgumentException, reaching UnsafeStaticLongFieldAccessorImpl.getShort through the public reflection API
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {

    public static long value = 42L;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticLongFieldAccessorImplWitupTest.class.getField("value");

        // Sanity check: the long read path works on the same field.
        long l = f.getLong(null);
        if (l != 42L) {
            throw new RuntimeException("getLong returned " + l + ", expected 42");
        }

        // Target behavior: getShort on a long field must reject the conversion.
        try {
            short s = f.getShort(null);
            throw new RuntimeException("getShort returned " + s + " without IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Same behavior when an object argument is supplied for a static field.
        try {
            short s = f.getShort(new Object());
            throw new RuntimeException("getShort(Object) returned " + s + " without IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
