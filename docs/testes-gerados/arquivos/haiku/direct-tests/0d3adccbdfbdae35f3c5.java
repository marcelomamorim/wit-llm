/*
 * @test
 * @summary Verify that Field.getLong on an int field returns the widened value of the field
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static class Holder {
        public int v;
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("v");
        Holder h = new Holder();

        h.v = 42;
        check(f.getLong(h) == 42L, "positive int not widened to long");

        h.v = -5;
        check(f.getLong(h) == -5L, "negative int not sign-extended to long");

        h.v = Integer.MIN_VALUE;
        check(f.getLong(h) == (long) Integer.MIN_VALUE, "MIN_VALUE not widened correctly");

        h.v = Integer.MAX_VALUE;
        check(f.getLong(h) == (long) Integer.MAX_VALUE, "MAX_VALUE not widened correctly");

        h.v = 7;
        check(f.getLong(h) == f.getInt(h), "getLong should match getInt value");

        boolean threw = false;
        try {
            f.getLong("not a Holder");
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check(threw, "getLong on wrong object type should throw IllegalArgumentException");
    }
}
