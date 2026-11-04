/*
 * @test
 * @summary Checks observable Field.get behavior for volatile Object fields, which use UnsafeQualifiedObjectFieldAccessorImpl
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    static class Holder {
        volatile Object value;
        Holder() {}
    }

    static class Other {
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("value");

        Holder h = new Holder();
        String s = "abc";
        h.value = s;
        check(f.get(h) == s, "get should return the same reference stored in the volatile field");

        h.value = null;
        check(f.get(h) == null, "get should return null when the field is null");

        Object marker = new Object();
        f.set(h, marker);
        check(f.get(h) == marker, "get should observe value written by Field.set");

        f.set(h, Integer.valueOf(42));
        check(Integer.valueOf(42).equals(f.get(h)), "Object field should accept and return boxed values");

        boolean iae = false;
        try {
            f.get(new Other());
        } catch (IllegalArgumentException e) {
            iae = true;
        }
        check(iae, "get on an object of an unrelated class should throw IllegalArgumentException");

        boolean npe = false;
        try {
            f.get(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "get on null receiver for an instance field should throw NullPointerException");
    }
}
