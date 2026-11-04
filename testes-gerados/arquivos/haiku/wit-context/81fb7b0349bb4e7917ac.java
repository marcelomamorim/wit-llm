/*
 * @test
 * @summary Checks public Field.getInt behavior that reaches the int accessor path, including rejection of an object of an incompatible type
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static class Holder {
        public int value;
    }

    public static class SubHolder extends Holder {
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("value");

        Holder h = new Holder();
        h.value = 42;
        check(f.getInt(h) == 42, "getInt on matching instance should return field value");

        SubHolder sub = new SubHolder();
        sub.value = 7;
        check(f.getInt(sub) == 7, "getInt on subclass instance should return field value");

        boolean thrown = false;
        try {
            f.getInt(new Object());
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        check(thrown, "getInt with incompatible object should throw IllegalArgumentException");
    }
}
