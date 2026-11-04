/*
 * @test
 * @summary Verify that Field.getDouble on a boolean field reaches UnsafeQualifiedBooleanFieldAccessorImpl.getDouble and throws IllegalArgumentException
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    static class Holder {
        boolean flag = true;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("flag");

        boolean thrown = false;
        try {
            f.getDouble(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getDouble on boolean field");
        }

        // Boolean access through the same accessor must still work.
        if (f.getBoolean(h) != true) {
            throw new RuntimeException("getBoolean returned unexpected value");
        }

        // Repeated getDouble calls must consistently reject the access.
        thrown = false;
        try {
            f.getDouble(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Second getDouble call did not throw IllegalArgumentException");
        }
    }
}
