/*
 * @test
 * @summary Verify that reading a static float field as int through the public Field API throws IllegalArgumentException, while getFloat still returns the stored value
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static class Holder {
        public static float F = 1.5f;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("F");

        float v = f.getFloat(null);
        if (v != 1.5f) {
            throw new RuntimeException("expected 1.5 but got " + v);
        }

        boolean thrown = false;
        try {
            f.getInt(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getInt should throw IllegalArgumentException");
        }
    }
}
