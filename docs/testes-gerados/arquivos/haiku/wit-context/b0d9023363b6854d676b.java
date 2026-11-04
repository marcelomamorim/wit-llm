/*
 * @test
 * @summary Verify that Field.getBoolean on a float field throws IllegalArgumentException, reaching UnsafeQualifiedFloatFieldAccessorImpl.getBoolean through the public reflection API
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static class Holder {
        public float f = 1.5f;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field field = Holder.class.getField("f");

        boolean thrown = false;
        try {
            field.getBoolean(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getBoolean on float field");
        }

        if (field.getFloat(h) != 1.5f) {
            throw new AssertionError("getFloat returned unexpected value");
        }
    }
}
