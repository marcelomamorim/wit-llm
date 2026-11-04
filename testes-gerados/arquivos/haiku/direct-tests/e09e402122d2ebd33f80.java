/*
 * @test
 * @summary Field.getInt on a static final long field must throw IllegalArgumentException, while get and getLong return the value
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {
    public static final long VALUE = 42L;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getField("VALUE");

        long l = f.getLong(null);
        if (l != 42L) {
            throw new AssertionError("getLong returned " + l);
        }

        Object o = f.get(null);
        if (!Long.valueOf(42L).equals(o)) {
            throw new AssertionError("get returned " + o);
        }

        try {
            f.getInt(null);
            throw new AssertionError("getInt did not throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
