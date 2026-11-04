/*
 * @test
 * @summary Verify that getDouble on a volatile long field reached through the public Field API widens the stored long value
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile long value;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("value");
        Holder h = new Holder();

        h.value = 42L;
        check(f.getDouble(h) == 42.0d, "getDouble(42L) should be 42.0");

        h.value = -7L;
        check(f.getDouble(h) == -7.0d, "getDouble(-7L) should be -7.0");

        h.value = 0L;
        check(f.getDouble(h) == 0.0d, "getDouble(0L) should be 0.0");

        long big = (1L << 53) + 1L;
        h.value = big;
        check(f.getDouble(h) == (double) big, "getDouble should match (double) widening for large long");

        h.value = Long.MAX_VALUE;
        check(f.getDouble(h) == (double) Long.MAX_VALUE, "getDouble should match widening for Long.MAX_VALUE");

        h.value = Long.MIN_VALUE;
        check(f.getDouble(h) == (double) Long.MIN_VALUE, "getDouble should match widening for Long.MIN_VALUE");

        h.value = 123456789L;
        check(f.getDouble(h) == (double) f.getLong(h), "getDouble should be consistent with getLong widening");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
