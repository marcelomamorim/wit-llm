/*
 * @test
 * @summary Verify that Field.getInt on a boolean field reaches UnsafeBooleanFieldAccessorImpl.getInt and throws IllegalArgumentException
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public static class Holder {
        public boolean flag = true;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("flag");

        boolean b = f.getBoolean(h);
        if (!b) {
            throw new RuntimeException("getBoolean should return true for flag");
        }

        try {
            int v = f.getInt(h);
            throw new RuntimeException("expected IllegalArgumentException from getInt, got " + v);
        } catch (IllegalArgumentException expected) {
            // expected: getInt on a boolean field is not a valid widening access
        }
    }
}
