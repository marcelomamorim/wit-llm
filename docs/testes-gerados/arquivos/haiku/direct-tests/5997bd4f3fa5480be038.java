/*
 * @test
 * @summary Verify public Field.getShort behavior backed by UnsafeQualifiedShortFieldAccessorImpl for a volatile short instance field
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class Holder {
        public volatile short s = 42;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("s");

        check(f.getShort(h) == 42, "initial getShort should return 42");

        f.setShort(h, (short) -7);
        check(f.getShort(h) == -7, "getShort should observe value set by setShort");

        check(f.getInt(h) == -7, "getInt should widen the value returned by getShort");

        boolean npe = false;
        try {
            f.getShort(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "getShort(null) on instance field should throw NullPointerException");

        boolean iae = false;
        try {
            f.getShort(new Object());
        } catch (IllegalArgumentException e) {
            iae = true;
        }
        check(iae, "getShort with object of wrong type should throw IllegalArgumentException");
    }
}
