/*
 * @test
 * @summary Verifies that reading a float field as int via the public Field API throws IllegalArgumentException, reaching UnsafeFloatFieldAccessorImpl.getInt
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public float value = 1.5f;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeFloatFieldAccessorImplWitupTest.class.getField("value");
        UnsafeFloatFieldAccessorImplWitupTest t = new UnsafeFloatFieldAccessorImplWitupTest();

        if (f.getFloat(t) != 1.5f) {
            throw new AssertionError("getFloat returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getInt(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getInt on float field");
        }
    }
}
