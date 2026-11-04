/*
 * @test
 * @summary Verifies Field.getBoolean and Field.setBoolean on a boolean instance field, including IllegalArgumentException for an object of the wrong type
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    static class Holder {
        boolean flag;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("flag");
        f.setAccessible(true);

        Holder h = new Holder();
        if (f.getBoolean(h)) {
            throw new RuntimeException("default value of boolean field should be false");
        }

        f.setBoolean(h, true);
        if (!f.getBoolean(h)) {
            throw new RuntimeException("getBoolean should return true after setBoolean(true)");
        }

        f.setBoolean(h, false);
        if (f.getBoolean(h)) {
            throw new RuntimeException("getBoolean should return false after setBoolean(false)");
        }

        try {
            f.getBoolean("not a Holder");
            throw new RuntimeException("expected IllegalArgumentException for object of wrong type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
