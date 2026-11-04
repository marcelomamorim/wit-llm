/*
 * @test
 * @summary Verifies that Field.getInt on a static final long field throws IllegalArgumentException and that getLong still returns the value
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static final long VALUE = 42L;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getField("VALUE");

        if (f.getLong(null) != 42L) {
            throw new RuntimeException("getLong returned unexpected value");
        }

        try {
            f.getInt(null);
            throw new RuntimeException("expected IllegalArgumentException from getInt");
        } catch (IllegalArgumentException expected) {
            // expected behavior
        }
    }
}
