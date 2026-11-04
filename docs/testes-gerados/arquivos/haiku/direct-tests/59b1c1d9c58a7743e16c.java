/*
 * @test
 * @summary Verifies that Field.getLong on a char field returns the widened char value, exercising UnsafeCharacterFieldAccessorImpl.getLong through the public reflection API
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static class Holder {
        public char c = 'A';
        public char max = '\uFFFF';
        public char zero;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();

        Field c = Holder.class.getDeclaredField("c");
        check(c.getLong(h) == 65L, "getLong of 'A' should be 65");

        Field max = Holder.class.getDeclaredField("max");
        check(max.getLong(h) == 65535L, "getLong of max char should be 65535");

        Field zero = Holder.class.getDeclaredField("zero");
        check(zero.getLong(h) == 0L, "getLong of default char should be 0");

        h.c = 'B';
        check(c.getLong(h) == 66L, "getLong should reflect updated char value");
    }
}
