/*
 * @test
 * @summary Verifies that Field.getLong on an int field returns the widened int value, including sign extension and updates.
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {
    int positive = 42;
    int negative = -7;
    int min = Integer.MIN_VALUE;
    int max = Integer.MAX_VALUE;
    int zero = 0;

    public static void main(String[] args) throws Exception {
        UnsafeIntegerFieldAccessorImplWitupTest t = new UnsafeIntegerFieldAccessorImplWitupTest();
        check("positive", 42L, t);
        check("negative", -7L, t);
        check("min", (long) Integer.MIN_VALUE, t);
        check("max", (long) Integer.MAX_VALUE, t);
        check("zero", 0L, t);

        Field f = UnsafeIntegerFieldAccessorImplWitupTest.class.getDeclaredField("positive");
        f.setInt(t, 1234);
        long updated = f.getLong(t);
        if (updated != 1234L) {
            throw new RuntimeException("expected 1234 after setInt but got " + updated);
        }

        Field g = UnsafeIntegerFieldAccessorImplWitupTest.class.getDeclaredField("negative");
        long widened = g.getLong(t);
        if (widened != (long) g.getInt(t)) {
            throw new RuntimeException("getLong differs from widened getInt");
        }
    }

    static void check(String name, long expected, Object o) throws Exception {
        Field f = o.getClass().getDeclaredField(name);
        long actual = f.getLong(o);
        if (actual != expected) {
            throw new RuntimeException(name + ": expected " + expected + " but got " + actual);
        }
    }
}
