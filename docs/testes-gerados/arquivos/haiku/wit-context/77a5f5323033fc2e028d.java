/*
 * @test
 * @summary Verifies that reading a short static field as boolean through the public Field API throws IllegalArgumentException, while getShort still returns the value.
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {
    public static short sval = 7;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticShortFieldAccessorImplWitupTest.class.getField("sval");

        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getBoolean on short static field");
        }

        if (f.getShort(null) != 7) {
            throw new AssertionError("getShort returned unexpected value");
        }
    }
}
