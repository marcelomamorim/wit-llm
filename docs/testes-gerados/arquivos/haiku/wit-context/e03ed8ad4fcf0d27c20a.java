/*
 * @test
 * @summary Verify Field.getLong on a volatile long field reaches the qualified long accessor and rejects receivers of an incompatible type
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile long value;
    }

    public static class Other {
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("value");

        Holder h = new Holder();
        h.value = 0L;
        check(f.getLong(h) == 0L, "default value must be 0");

        h.value = 123456789012L;
        check(f.getLong(h) == 123456789012L, "getLong must return the current volatile value");
        check(f.get(h).equals(Long.valueOf(123456789012L)), "get must box the same value");

        h.value = -1L;
        check(f.getLong(h) == -1L, "getLong must observe negative values");

        boolean thrown = false;
        try {
            f.getLong(new Other());
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        check(thrown, "getLong with an incompatible receiver must throw IllegalArgumentException");

        thrown = false;
        try {
            f.getLong("not a holder");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        check(thrown, "getLong with a String receiver must throw IllegalArgumentException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
