/*
 * @test
 * @summary Verify that a volatile boolean field accessed through Field.getChar rejects the conversion with IllegalArgumentException
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile boolean flag;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field field = Holder.class.getField("flag");
        Holder h = new Holder();

        h.flag = true;
        check(field.getBoolean(h) == true, "getBoolean should still return the stored value");

        boolean thrown = false;
        try {
            field.getChar(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "getChar on a boolean field must throw IllegalArgumentException");

        thrown = false;
        try {
            field.getChar(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "getChar with null obj must throw IllegalArgumentException");
    }
}
