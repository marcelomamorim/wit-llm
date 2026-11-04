/*
 * @test
 * @summary Verifies Field.getFloat on a volatile long field, which is served by UnsafeQualifiedLongFieldAccessorImpl.getFloat, returns the widened long value
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile long v;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("v");

        h.v = 0L;
        check(f.getFloat(h) == 0.0f, "zero value");

        h.v = -42L;
        check(f.getFloat(h) == -42.0f, "negative value");

        h.v = 16777217L;
        check(f.getFloat(h) == (float) 16777217L, "precision loss value 2^24+1");

        h.v = Long.MAX_VALUE;
        check(f.getFloat(h) == (float) Long.MAX_VALUE, "Long.MAX_VALUE widening");

        h.v = 123456789L;
        check(f.getFloat(h) == (float) f.getLong(h), "getFloat must equal widened getLong");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
