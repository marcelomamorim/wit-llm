/*
 * @test
 * @summary Checks public Field.get behaviour that exercises UnsafeObjectFieldAccessorImpl.get, including the IllegalArgumentException for an incompatible receiver
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static class Holder {
        public Object value;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("value");

        Holder h = new Holder();
        h.value = "abc";
        Object got = f.get(h);
        if (!"abc".equals(got)) {
            throw new RuntimeException("unexpected value: " + got);
        }

        h.value = null;
        if (f.get(h) != null) {
            throw new RuntimeException("expected null value");
        }

        try {
            f.get(new Object());
            throw new RuntimeException("expected IllegalArgumentException for incompatible receiver");
        } catch (IllegalArgumentException expected) {
        }
    }
}
