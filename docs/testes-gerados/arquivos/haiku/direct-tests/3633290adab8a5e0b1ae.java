/*
 * @test
 * @summary Verifies that Field.getDouble on a reference-typed field throws IllegalArgumentException, exercising UnsafeObjectFieldAccessorImpl.getDouble through the public reflection API
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static class Holder {
        public Object ref = "value";
        public String text = "text";
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();

        Field refField = Holder.class.getField("ref");
        boolean thrown = false;
        try {
            refField.getDouble(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "getDouble on Object field must throw IllegalArgumentException");

        Field textField = Holder.class.getField("text");
        thrown = false;
        try {
            textField.getDouble(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "getDouble on String field must throw IllegalArgumentException");

        Object value = refField.get(h);
        check("value".equals(value), "get must still return the reference value after failed getDouble");

        h.ref = null;
        thrown = false;
        try {
            refField.getDouble(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "getDouble on null Object field must throw IllegalArgumentException");
    }
}
