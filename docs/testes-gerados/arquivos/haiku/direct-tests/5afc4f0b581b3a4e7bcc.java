/*
 * @test
 * @summary Verify that Field.getByte on a static float field throws IllegalArgumentException through the UnsafeStaticFloatFieldAccessorImpl accessor, while getFloat still returns the stored value
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static float value = 1.5f;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getField("value");

        if (f.getFloat(null) != 1.5f) {
            throw new RuntimeException("getFloat returned unexpected value");
        }

        try {
            f.getByte(null);
            throw new RuntimeException("expected IllegalArgumentException from getByte");
        } catch (IllegalArgumentException expected) {
            // expected behavior
        }
    }
}
