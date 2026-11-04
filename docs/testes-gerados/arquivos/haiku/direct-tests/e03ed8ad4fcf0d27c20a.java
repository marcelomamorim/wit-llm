/*
 * @test
 * @summary Verify Field.getLong on a volatile long field, which is served by UnsafeQualifiedLongFieldAccessorImpl.getLong
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    static class Holder {
        volatile long v;
    }

    static class Other {
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("v");
        f.setAccessible(true);
        Holder h = new Holder();

        h.v = 123456789012L;
        check(f.getLong(h) == 123456789012L, "getLong should return the stored long value");

        f.setLong(h, -42L);
        check(f.getLong(h) == -42L, "getLong should observe a value written by setLong");

        Object boxed = f.get(h);
        check(boxed instanceof Long, "get should return a Long for a long field");
        check(((Long) boxed).longValue() == -42L, "get should return the same value as getLong");

        boolean threw = false;
        try {
            f.getLong(new Other());
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check(threw, "getLong on an object of an unrelated type should throw IllegalArgumentException");
    }
}
