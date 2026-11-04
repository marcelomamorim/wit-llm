/*
 * @test
 * @summary Field.getShort on a static float field must throw IllegalArgumentException, reaching UnsafeStaticFloatFieldAccessorImpl.getShort
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {

    public static float value = 1.5f;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            f.getShort(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getShort on static float field");
        }

        if (f.getFloat(null) != 1.5f) {
            throw new AssertionError("getFloat must still return the static float value");
        }
    }
}
